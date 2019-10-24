#!/usr/bin/env bash

function IsPactBrokerRunning() {
    IS_RUNNING=`docker ps | grep 'pact-broker-docker_pact_broker'`
    if [[ "$IS_RUNNING" != "" ]]; then
        echo "Pact Broker is already running"
        exit 0
    fi
}

function RunPactBroker() {
    rm -rf /tmp/pact-broker-docker
    git clone https://github.com/pact-foundation/pact-broker-docker.git /tmp/pact-broker-docker
    cd /tmp/pact-broker-docker
    docker-compose up
    rm -rf /tmp/pact-broker-docker
}

IsPactBrokerRunning
RunPactBroker