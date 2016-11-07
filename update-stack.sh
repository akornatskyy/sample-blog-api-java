#!/bin/bash

STACK_NAME=test

cf='aws cloudformation'

$cf create-stack --stack-name $STACK_NAME \
	--template-body file://stack.json > /dev/null
$cf wait stack-create-complete --stack-name $STACK_NAME
