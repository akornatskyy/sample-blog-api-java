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