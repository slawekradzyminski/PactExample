package com.awesome.testing.contract.update;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.PactTestExecutionContext;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.core.model.RequestResponsePact;
import com.awesome.testing.contract.AbstractPactTest;
import com.awesome.testing.dto.information.IdNotAwareInformation;
import com.awesome.testing.dto.information.Information;

import static com.awesome.testing.dto.information.InformationField.ID;
import static com.awesome.testing.dto.information.InformationField.NAME;
import static com.awesome.testing.dto.information.InformationField.NATIONALITY;
import static com.awesome.testing.dto.information.InformationField.SALARY;
import static io.pactfoundation.consumer.dsl.LambdaDsl.newJsonBody;
import static org.assertj.core.api.Assertions.assertThat;

public class UpdateInformationTest extends AbstractPactTest {

    @Override
    protected RequestResponsePact createPact(PactDslWithProvider builder) {
        return builder
                .given("Two entries exist")
                .uponReceiving("PUT request")
                .path("/information/" + SAMPLE_ID)
                .method("PUT")
                .headers(getApplicationJsonHeader())
                .body(newJsonBody((root) -> {
                    root.stringValue(NAME.getValue(), SAMPLE_NAME);
                    root.stringValue(NATIONALITY.getValue(), SAMPLE_NATIONALITY);
                    root.numberValue(SALARY.getValue(), SAMPLE_SALARY);
                }).build())
                .willRespondWith()
                .status(200)
                .body(newJsonBody((root) -> {
                    root.numberValue(ID.getValue(), SAMPLE_ID);
                    root.stringValue(NAME.getValue(), SAMPLE_NAME);
                    root.stringValue(NATIONALITY.getValue(), SAMPLE_NATIONALITY);
                    root.numberValue(SALARY.getValue(), SAMPLE_SALARY);
                }).build())
                .headers(getApplicationJsonHeader())
                .toPact();
    }

    @Override
    protected void runTest(MockServer mockServer, PactTestExecutionContext context) {
        IdNotAwareInformation information =
                new IdNotAwareInformation(SAMPLE_NAME, SAMPLE_NATIONALITY, SAMPLE_SALARY);

        informationClient.overrideBackendUrl(mockServer.getUrl());
        Information informationAdded =
                informationClient.updateViaPut(information, SAMPLE_ID).getBody();

        assertThat(informationAdded.getId()).isEqualTo(SAMPLE_ID);
        assertThat(informationAdded.getName()).isEqualTo(SAMPLE_NAME);
        assertThat(informationAdded.getNationality()).isEqualTo(SAMPLE_NATIONALITY);
        assertThat(informationAdded.getSalary()).isEqualTo(SAMPLE_SALARY);
    }
}
