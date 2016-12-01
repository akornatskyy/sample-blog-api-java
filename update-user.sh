#!/bin/bash

if [ $# -eq 0 ]; then
    echo "Usage: $0 <user-name>"
    exit 1
fi

STACK_NAME=$1-user

cf='aws cloudformation'

cmd='create'

echo looking for stack
$cf describe-stacks --stack-name $STACK_NAME &>/dev/null

if [ "$?" == "0" ]; then
  echo stack exists... checking status
  s=$($cf describe-stacks --stack-name $STACK_NAME --output text \
    --query 'Stacks[0].StackStatus')
  echo stack status $s

  if [ "$s" == "ROLLBACK_COMPLETE" ] || [ "$s" == "ROLLBACK_FAILED" ] || \
     [ "$s" == "DELETE_FAILED" ] || [ "$s" == "UPDATE_ROLLBACK_FAILED" ] ; then
    echo deleting stack
    $cf delete-stack --stack-name $STACK_NAME
    $cf wait stack-delete-complete --stack-name $STACK_NAME
  else
    cmd='update'
  fi
fi

echo $cmd stack initiated
$cf $cmd-stack --stack-name $STACK_NAME --capabilities CAPABILITY_NAMED_IAM \
  --parameters ParameterKey=UserName,ParameterValue=$1 \
  --template-body file://user.json > /dev/null
if [ "$?" == "0" ]; then
  $cf wait stack-$cmd-complete --stack-name $STACK_NAME
fi

$cf describe-stacks --stack-name $STACK_NAME \
  --query 'Stacks[0].Outputs.{AccessKey:[?OutputKey==`AccessKey`].OutputValue,SecretKey:[?OutputKey==`SecretKey`].OutputValue}'
