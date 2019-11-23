#!/usr/bin/env bash

set -x
rm -rf example-consumer-messi/build/pacts
rm -rf example-consumer-ronaldo/build/pacts
./gradlew clean
if [[ $? -ne 0 ]] ; then
    exit 1
fi
./gradlew :example-consumer-ronaldo:build
if [[ $? -ne 0 ]] ; then
    exit 1
fi
./gradlew :example-consumer-ronaldo:pactPublish
if [[ $? -ne 0 ]] ; then
    exit 1
fi
./gradlew :example-consumer-messi:build
if [[ $? -ne 0 ]] ; then
    exit 1
fi
./gradlew :example-consumer-messi:pactPublish
if [[ $? -ne 0 ]] ; then
    exit 1
fi
./gradlew :example-provider:build
if [[ $? -ne 0 ]] ; then
    exit 1
fi
./gradlew :example-provider:pactVerify -PrequestVerification=true -Ppact.verifier.publishResults='true'
if [[ $? -ne 0 ]] ; then
    exit 1
fi