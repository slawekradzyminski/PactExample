package com.awesome.testing.contract.create;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.PactTestExecutionContext;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.core.model.RequestResponsePact;
import com.awesome.testing.dto.information.IdNotAwareInformation;
import com.awesome.testing.dto.information.Information;
import com.awesome.testing.contract.AbstractPactTest;

import static com.awesome.testing.dto.information.InformationField.ID;
import static com.awesome.testing.dto.information.InformationField.NAME;
import static com.awesome.testing.dto.information.InformationField.NATIONALITY;
import static com.awesome.testing.dto.information.InformationField.SALARY;
import static io.pactfoundation.consumer.dsl.LambdaDsl.newJsonBody;
import static org.assertj.core.api.Assertions.assertThat;

public class CreateInformationTest extends AbstractPactTest {

    @Override
    protected RequestResponsePact createPact(PactDslWithProvider builder) {
        return builder
                .given("Two entries exist")
                .uponReceiving("POST request")
                .path("/information")
                .method("POST")
                .headers(getApplicationJsonHeader())
                .body(newJsonBody((root) -> {
                    root.stringValue(NAME.getValue(), SAMPLE_NAME);
                    root.stringValue(NATIONALITY.getValue(), SAMPLE_NATIONALITY);
                    root.numberValue(SALARY.getValue(), SAMPLE_SALARY);
                }).build())
                .willRespondWith()
                .status(201)
                .body(newJsonBody((root) -> {
                    root.numberType(ID.getValue(), 3);
                    root.stringValue(NAME.getValue(), SAMPLE_NAME);
                    root.stringValue(NATIONALITY.getValue(), SAMPLE_NATIONALITY);
                    root.numberValue(SALARY.getValue(), SAMPLE_SALARY);
                }).build())
                .headers(getApplicationJsonHeader())
                .toPact();
    }

    @Override
    protected void runTest(MockServer mockServer, PactTestExecutionContext context) {
        IdNotAwareInformation information = new IdNotAwareInformation
                (SAMPLE_NAME, SAMPLE_NATIONALITY, SAMPLE_SALARY);

        informationClient.overrideBackendUrl(mockServer.getUrl());
        Information informationAdded = informationClient.add(information).getBody();

        assertThat(informationAdded.getId()).isGreaterThan(0);
        assertThat(informationAdded.getName()).isEqualTo(SAMPLE_NAME);
        assertThat(informationAdded.getNationality()).isEqualTo(SAMPLE_NATIONALITY);
        assertThat(informationAdded.getSalary()).isEqualTo(SAMPLE_SALARY);
    }
}
