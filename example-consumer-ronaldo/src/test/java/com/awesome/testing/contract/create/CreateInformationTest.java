package com.awesome.testing.contract.create;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.PactTestExecutionContext;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.core.model.RequestResponsePact;
import com.awesome.testing.Information;
import com.awesome.testing.contract.AbstractPactTest;

import static io.pactfoundation.consumer.dsl.LambdaDsl.newJsonBody;
import static org.assertj.core.api.Assertions.assertThat;

public class CreateInformationTest extends AbstractPactTest {

    private static final String SAMPLE_NAME = "JohnnyBravo";
    private static final String SAMPLE_NATIONALITY = "Japan";
    private static final int SAMPLE_SALARY = 4444;

    @Override
    protected RequestResponsePact createPact(PactDslWithProvider builder) {
        return builder
                .given("Two entries exist")
                .uponReceiving("Add information request")
                .path("/information")
                .method("POST")
                .headers(getJsonHeader())
                .body(newJsonBody((root) -> {
                    root.stringType("name", SAMPLE_NAME);
                    root.stringValue("nationality", SAMPLE_NATIONALITY);
                    root.numberType("salary", SAMPLE_SALARY);
                }).build())
                .willRespondWith()
                .status(201)
                .body(newJsonBody((root) -> {
                    root.numberType("id", 3);
                    root.stringValue("name", SAMPLE_NAME);
                    root.stringValue("nationality", SAMPLE_NATIONALITY);
                    root.numberValue("salary", SAMPLE_SALARY);
                }).build())
                .headers(getJsonHeader())
                .toPact();
    }

    @Override
    protected void runTest(MockServer mockServer, PactTestExecutionContext context) {
        Information information = new Information(SAMPLE_NAME, SAMPLE_NATIONALITY, SAMPLE_SALARY);

        providerService.overrideBackendUrl(mockServer.getUrl());
        Information informationAdded = providerService.add(information).getBody();

        assertThat(informationAdded.getName()).isEqualTo(SAMPLE_NAME);
        assertThat(informationAdded.getNationality()).isEqualTo(SAMPLE_NATIONALITY);
        assertThat(informationAdded.getSalary()).isEqualTo(SAMPLE_SALARY);
    }
}
