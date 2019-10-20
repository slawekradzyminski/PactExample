### Setup Pact Broker via Docker compose
[Packt Broker Docker](https://github.com/pact-foundation/pact-broker-docker)

### Build
`./gradlew :example-consumer-ronaldo:build
./gradlew :example-consumer-messi:build
./gradlew :example-provider:build`

### Publish pacts
`./gradlew :example-consumer-ronaldo:pactPublish
./gradlew :example-consumer-messi:pactPublish`

### Verify pacts
`./gradlew :example-provider:pactVerify`