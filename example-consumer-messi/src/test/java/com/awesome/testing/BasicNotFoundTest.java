package com.awesome.testing;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.PactTestExecutionContext;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import org.springframework.web.client.HttpClientErrorException;


import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class BasicNotFoundTest extends AbstractPactTest {

    private static final String NONEXISTING_NAME = "Nonexisting";

    @Override
    @Pact(provider = PROVIDER_NAME, consumer = CUSTOMER_NAME)
    public RequestResponsePact createPact(PactDslWithProvider builder) {
        return builder
                .given("Default nationality")
                .uponReceiving("Request that cannot be fulfilled")
                .path("/information")
                .query("name=" + NONEXISTING_NAME)
                .method("GET")
                .willRespondWith()
                .status(404)
                .toPact();
    }

    @Override
    protected void runTest(MockServer mockServer, PactTestExecutionContext context) {
        providerService.overrideBackendUrl(mockServer.getUrl());
        assertThatThrownBy(() -> providerService.getResponseForName(NONEXISTING_NAME))
                .isInstanceOf(HttpClientErrorException.class)
                .hasMessageContaining("404");
    }

}
