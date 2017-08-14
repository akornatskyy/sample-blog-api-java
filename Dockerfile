FROM openjdk:8-jdk-alpine

MAINTAINER Andriy Kornatskyy <andriy.kornatskyy@live.com>

ENV MAVEN_VERSION=3.5.0
ENV APP_VERSION=1.0-SNAPSHOT

RUN set -ex \
    \
    && apk add --no-cache --virtual .build-deps \
        ca-certificates \
        openssl \
        git \
    \
    && mkdir -p /usr/local/maven \
    && wget -c https://www-eu.apache.org/dist/maven/maven-3/${MAVEN_VERSION}/binaries/apache-maven-${MAVEN_VERSION}-bin.tar.gz \
        -O - | tar -xzC /usr/local/maven --strip-components=1 \
    \
    && git clone https://github.com/akornatskyy/sample-blog-api-java.git src \
    \
    && cd src \
    && /usr/local/maven/bin/mvn -T"$(getconf _NPROCESSORS_ONLN)" clean verify \
    && mkdir -p /app \
    && mv /src/modules/app/target/sample-blog-app-${APP_VERSION}.jar /app \
    \
    && rm -rf /src ~/.m2 /usr/local/maven \
    && apk del .build-deps

CMD java -jar /app/sample-blog-app-$APP_VERSION.jar
