### Theory
[Testing micro services - the sane way](https://medium.com/@copyconstruct/testing-microservices-the-sane-way-9bb31d158c16)

[Fowler - customer driven contract tests](https://martinfowler.com/articles/consumerDrivenContracts.html)

[7 reasons for consumer driven contracts](https://reflectoring.io/7-reasons-for-consumer-driven-contracts/)

Contract test great introduction
[Introduction](https://kreuzwerker.de/post/introduction-to-consumer-driven-contract-testing)
[Writing tests](https://kreuzwerker.de/post/writing-contract-tests-with-pact-in-spring-boot)
[Continuous Integration](https://kreuzwerker.de/post/integrating-contract-tests-into-build-pipelines-with-pact-broker-and)
[Lessons Learned](https://kreuzwerker.de/post/contract-tests-lessons-learned)

Testing message queues with Pact
[Pact blog 1](https://dius.com.au/2017/09/22/contract-testing-serverless-and-asynchronous-applications/)
[Pact blog 2](https://dius.com.au/2018/10/01/contract-testing-serverless-and-asynchronous-applications---part-2/)
[Reflectoring blog](https://reflectoring.io/cdc-pact-messages/)

### Setup Pact Broker via Docker compose
[Packt Broker Docker](https://github.com/pact-foundation/pact-broker-docker)

### Build
`./gradlew :example-consumer-ronaldo:build`

`./gradlew :example-consumer-messi:build`

`./gradlew :example-provider:build`

### Publish pacts
`./gradlew :example-consumer-ronaldo:pactPublish`

`./gradlew :example-consumer-messi:pactPublish`

### Verify pacts
`./gradlew :example-provider:pactVerify`

### More playground (a bit dated)
https://github.com/Mikuu/Pact-JVM-Example
https://github.com/DiUS/pact-workshop-jvm
