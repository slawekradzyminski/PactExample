package com.awesome.testing.contract.root;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.PactTestExecutionContext;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.core.model.RequestResponsePact;
import com.awesome.testing.contract.AbstractPactTest;
import com.awesome.testing.dto.information.Information;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static com.awesome.testing.controller.RootController.RONALDO;
import static org.assertj.core.api.Assertions.assertThat;

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
        informationClient.overrideBackendUrl(mockServer.getUrl());
        ResponseEntity<Information> responseForName = informationClient.getResponseForName(RONALDO);
        assertThat(responseForName.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
