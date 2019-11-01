package com.awesome.testing.contract;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.PactTestExecutionContext;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.core.model.RequestResponsePact;
import org.springframework.web.client.HttpClientErrorException;

import static com.awesome.testing.InformationController.RONALDO;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class StateDatabaseEmptyTest extends AbstractPactTest {

    @Override
    protected RequestResponsePact createPact(PactDslWithProvider builder) {
        return builder
                .given("Empty database state")
                .uponReceiving("Empty database state")
                .path("/information")
                .query("name=" + RONALDO)
                .method("GET")
                .willRespondWith()
                .status(404)
                .toPact();
    }

    @Override
    protected void runTest(MockServer mockServer, PactTestExecutionContext context) {
        providerService.overrideBackendUrl(mockServer.getUrl());
        assertThatThrownBy(() -> providerService.getResponseForName(RONALDO))
                .isInstanceOf(HttpClientErrorException.class)
                .hasMessageContaining("404");
    }
}
