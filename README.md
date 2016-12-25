# Sample Blog API

[![Build Status](https://travis-ci.org/akornatskyy/sample-blog-api-java.svg?branch=master)](https://travis-ci.org/akornatskyy/sample-blog-api-java)

A simple blog API written using Java.

## Build

Run any checks to verify the package is valid and meets 
quality criteria.

    mvn -T4 clean verify

## Run

Run application as an executable jar.

    java -jar modules/app/target/sample-blog-app-1.0-SNAPSHOT.jar
    
## AWS

Create S3 bucket for `jar` file and set environment variable:

    export S3_JAR_BUCKET=<my-unique-bucket-name>
    
Deploy `user` Cloud Formation stack (optional):

    ./update-user.sh sample-blog

Switch to just created user credentials (see `~/.aws/credentials`) 
and deploy `sample-blog-api` stack:

    ./update-stack.sh sample-blog-api

Delete stacks:

    aws cloudformation delete-stack --stack-name sample-blog-api
    aws cloudformation delete-stack --stack-name sample-blog-user

# curl

Validation error:

    $ curl -si -H 'Content-Type: application/json' \
        -X POST -d '{}' \
        http://localhost:8080/api/v1/signin
        
    HTTP/1.1 400
    Content-Type: application/json;charset=UTF-8
    Transfer-Encoding: chunked
    Connection: close
    
    {"password":["Required field cannot be left blank."],
    "username":["Required field cannot be left blank."]}

General error:

    $ curl -si -H 'Content-Type: application/json' \
        -X POST -d '{"username":"js", "password":"password"}' \
        http://localhost:8080/api/v1/signin
        
    HTTP/1.1 400
    Content-Type: application/json;charset=UTF-8
    Transfer-Encoding: chunked
    Connection: close
    
    {"__ERROR__":["The account is locked. Contact system administrator, please."]}

Valid:

    $ curl -si -H 'Content-Type: application/json' \
    -X POST -d '{"username":"demo", "password":"password"}' \
    http://localhost:8080/api/v1/signin
    
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