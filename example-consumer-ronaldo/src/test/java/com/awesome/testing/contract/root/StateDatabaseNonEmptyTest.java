package com.awesome.testing.contract.root;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.PactTestExecutionContext;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.core.model.RequestResponsePact;
import com.awesome.testing.dto.information.Information;
import com.awesome.testing.contract.AbstractPactTest;

import static com.awesome.testing.controller.RootController.RONALDO;
import static com.awesome.testing.dto.information.InformationField.NAME;
import static com.awesome.testing.dto.information.InformationField.NATIONALITY;
import static com.awesome.testing.dto.information.InformationField.SALARY;
import static io.pactfoundation.consumer.dsl.LambdaDsl.newJsonBody;
import static org.assertj.core.api.Assertions.assertThat;

public class StateDatabaseNonEmptyTest extends AbstractPactTest {

    @Override
    protected RequestResponsePact createPact(PactDslWithProvider builder) {

        return builder
                .given("Two entries exist")
                .uponReceiving("Two entries exist")
                .path("/information")
                .query("name=" + RONALDO)
                .method("GET")
                .willRespondWith()
                .headers(getApplicationJsonHeader())
                .status(200)
                .body(newJsonBody((root) -> {
                    root.stringType(NAME.getValue(), RONALDO);
                    root.stringValue(NATIONALITY.getValue(), "Portugal");
                    root.numberType(SALARY.getValue(), 80000);
                }).build())
                .toPact();
    }

    @Override
    protected void runTest(MockServer mockServer, PactTestExecutionContext context) {
        informationClient.overrideBackendUrl(mockServer.getUrl());
        Information information = informationClient.getResponseForName(RONALDO).getBody();
        assertThat(information).isNotNull();
        assertThat(information.getNationality()).isEqualTo("Portugal");
        assertThat(information.getName()).isEqualTo(RONALDO);
    }
}
