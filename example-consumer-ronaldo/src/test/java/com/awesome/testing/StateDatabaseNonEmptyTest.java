package com.awesome.testing;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.PactTestExecutionContext;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.core.model.RequestResponsePact;

import java.util.HashMap;
import java.util.Map;

import static com.awesome.testing.InformationController.RONALDO;
import static io.pactfoundation.consumer.dsl.LambdaDsl.newJsonBody;
import static org.assertj.core.api.Assertions.assertThat;

public class StateDatabaseNonEmptyTest extends AbstractPactTest {

    @Override
    protected RequestResponsePact createPact(PactDslWithProvider builder) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        return builder
                .given("Two entries exist")
                .uponReceiving("Two entries exist")
                .path("/information")
                .query("name=" + RONALDO)
                .method("GET")
                .willRespondWith()
                .headers(headers)
                .status(200)
                .body(newJsonBody((root) -> {
                    root.numberType("salary", 80000);
                    root.stringType("name", RONALDO);
                    root.stringValue("nationality", "Portugal");
                }).build())
                .toPact();
    }

    @Override
    protected void runTest(MockServer mockServer, PactTestExecutionContext context) {
        providerService.overrideBackendUrl(mockServer.getUrl());
        Information information = providerService.getResponseForName(RONALDO).getBody();
        assertThat(information).isNotNull();
        assertThat(information.getNationality()).isEqualTo("Portugal");
        assertThat(information.getName()).isEqualTo(RONALDO);
    }
}
