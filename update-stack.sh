#!/bin/bash

if [ $# -eq 0 ]; then
    echo "Usage: $0 <stack-name>"
    exit 1
fi

STACK_NAME=$1

cf='aws cloudformation'

cmd='create'

echo looking for stack
$cf describe-stacks --stack-name $STACK_NAME &>/dev/null

if [ "$?" == "0" ]; then
  echo stack exists... checking status
  s=$($cf describe-stacks --stack-name $STACK_NAME --output text \
    --query 'Stacks[0].StackStatus')
  echo stack status $s

  if [ "$s" == "ROLLBACK_COMPLETE" ]; then
    echo deleting stack
    $cf delete-stack --stack-name $STACK_NAME
    $cf wait stack-delete-complete --stack-name $STACK_NAME
  else
    cmd='update'
  fi
fi

echo $cmd stack initiated
$cf $cmd-stack --stack-name $STACK_NAME --capabilities CAPABILITY_NAMED_IAM \
  --template-body file://stack.json > /dev/null
if [ "$?" == "0" ]; then
  $cf wait stack-$cmd-complete --stack-name $STACK_NAME
fi