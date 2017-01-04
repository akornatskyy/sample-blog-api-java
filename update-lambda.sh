#!/bin/bash

if [ $# -eq 0 ]; then
  if [ "$STACK_NAME" == "" ]; then
    echo "Usage: $0 <stack-name>"
    exit 1
  fi
else
  STACK_NAME=$1
fi

if [ "$S3_JAR_BUCKET" == "" ]; then
  echo "ERROR: environment variable S3_JAR_BUCKET is not set."
  echo ""
  echo "export S3_JAR_BUCKET=<my-unique-bucket-name>"
  exit 1
fi

JAR_FILE=sample-blog-aws-1.0-SNAPSHOT.jar

echo "packaging jar file"
mvn -q -T4 -DskipTests clean package

echo "uploading..."
aws s3 cp modules/aws/target/${JAR_FILE} s3://${S3_JAR_BUCKET}

echo "updating code"
aws lambda update-function-code --function-name ${STACK_NAME}-request-handler \
  --s3-bucket $S3_JAR_BUCKET --s3-key ${JAR_FILE}