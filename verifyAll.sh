#!/usr/bin/env bash

PORT_IN_USE=`lsof -i:8080`
    if [[ "$PORT_IN_USE" != "" ]]; then
        echo "Port 8080 in use. Please open if first."
        exit 1
    fi

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