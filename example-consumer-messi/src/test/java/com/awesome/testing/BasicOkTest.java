package com.awesome.testing;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.PactTestExecutionContext;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.core.model.RequestResponsePact;

import java.util.HashMap;
import java.util.Map;

import static com.awesome.testing.InformationController.MESSI;
import static io.pactfoundation.consumer.dsl.LambdaDsl.newJsonBody;
import static org.assertj.core.api.Assertions.assertThat;

public class BasicOkTest extends AbstractPactTest {

    @Override
    public RequestResponsePact createPact(PactDslWithProvider builder) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        return builder
                .given("Default nationality")
                .uponReceiving("Pact JVM example Pact interaction")
                .path("/information")
                .query("name=Messi")
                .method("GET")
                .willRespondWith()
                .headers(headers)
                .status(200)
                .body(newJsonBody((root) -> {
                    root.numberType("salary", 7500);
                    root.stringType("name", "Leo Messi");
                    root.stringType("nationality", "Argentina");
                }).build())
                .toPact();
    }

    @Override
    protected void runTest(MockServer mockServer, PactTestExecutionContext context) {
        providerService.overrideBackendUrl(mockServer.getUrl());
        Information information = providerService.getResponseForName(MESSI).getBody();
        assertThat(information).isNotNull();
        assertThat(information.getName()).isEqualTo("Leo Messi");
    }

}
