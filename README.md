# Sample Blog API

[![Build Status](https://travis-ci.org/akornatskyy/sample-blog-api-java.svg?branch=master)](https://travis-ci.org/akornatskyy/sample-blog-api-java)

A simple blog API written using Java.

## Build

Run any checks to verify the package is valid and meets 
quality criteria.

    mvn verify

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