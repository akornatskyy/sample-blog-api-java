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
  cmd='update'
fi

echo $cmd stack initiated
$cf $cmd-stack --stack-name $STACK_NAME --capabilities CAPABILITY_NAMED_IAM \
  --template-body file://stack.json > /dev/null
if [ "$?" == "0" ]; then
  $cf wait stack-$cmd-complete --stack-name $STACK_NAME
fi