# Sample Blog API

[![tests](https://github.com/akornatskyy/sample-blog-api-java/actions/workflows/tests.yaml/badge.svg)](https://github.com/akornatskyy/sample-blog-api-java/actions/workflows/tests.yaml)

A simple blog API written using Java.

## Build

Run any checks to verify the package is valid and meets
quality criteria.

    mvn -T1C clean verify

## Run

Run application as an executable jar.

    java -jar sample-blog-app/target/sample-blog-app-1.0-SNAPSHOT.jar
    java -Dspring.profiles.active=jdbc \
      -jar sample-blog-app/target/sample-blog-app-1.0-SNAPSHOT.jar

## Docker

Run application with docker.

    docker run -it --rm -p 8080:8080 akorn/sample-blog-api-java

## AWS

Create S3 bucket for `jar` file and set environment variable:

    export S3_JAR_BUCKET=<my-unique-bucket-name>

Deploy `user` Cloud Formation stack (optional):

    ./update-user.sh sample-blog

Upload jar file to S3 bucket:

    aws s3 cp sample-blog-aws/target/sample-blog-aws-1.0-SNAPSHOT.jar \
      s3://$S3_JAR_BUCKET/

Switch to just created user credentials (see `~/.aws/credentials`)
and deploy `sample-blog-api` stack:

    ./update-stack.sh sample-blog-api

During development AWS Lambda function code can be updated with:

    aws lambda update-function-code \
      --function-name sample-blog-request-handler \
      --s3-bucket $S3_JAR_BUCKET --s3-key sample-blog-aws-1.0-SNAPSHOT.jar

or using bash script:

    ./update-lambda.sh sample-blog-api

Delete stacks:

    aws cloudformation delete-stack --stack-name sample-blog-api
    aws cloudformation delete-stack --stack-name sample-blog-user

# curl

> In case of AWS lambda deployment use the host name printed during stack
> update, for example:
> https://zy6ymiy7yf.execute-api.eu-west-1.amazonaws.com/v1/signin

Validation error:

    $ curl -si -H 'Content-Type: application/json' \
        -X POST -d '{}' \
        http://localhost:8080/signin

    HTTP/1.1 400
    Content-Type: application/json;charset=UTF-8
    Transfer-Encoding: chunked
    Connection: close

    {"password":["Required field cannot be left blank."],
    "username":["Required field cannot be left blank."]}

General error:

    $ curl -si -H 'Content-Type: application/json' \
        -X POST -d '{"username":"js", "password":"password"}' \
        http://localhost:8080/signin

    HTTP/1.1 400
    Content-Type: application/json;charset=UTF-8
    Transfer-Encoding: chunked
    Connection: close

    {"__ERROR__":["The account is locked. Contact system administrator, please."]}

Valid:

    $ curl -si -H 'Content-Type: application/json' \
    -X POST -d '{"username":"demo", "password":"password"}' \
    http://localhost:8080/signin

    HTTP/1.1 200
    Content-Type: application/json;charset=UTF-8
    Transfer-Encoding: chunked

    {"username":"demo"}

## Tools

Byte code analysis to determine missing or unused
dependencies.

    mvn dependency:analyze

Displays all dependencies that have newer versions
available.

    mvn versions:display-dependency-updates
    mvn versions:display-plugin-updates

Update dependencies to the latest available versions:

    mvn versions:use-latest-versions

Update versions specified in properties:

    mvn versions:update-properties

## Links

- [Maven: The Complete Reference](http://books.sonatype.com/mvnref-book/reference/index.html)
