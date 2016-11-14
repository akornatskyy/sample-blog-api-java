#!/bin/bash

STACK_NAME=test

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