package com.awesome.testing.contract;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.PactTestExecutionContext;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.core.model.RequestResponsePact;
import com.awesome.testing.Information;
import org.springframework.http.MediaType;

import java.util.HashMap;
import java.util.Map;

import static com.awesome.testing.InformationController.MESSI;
import static io.pactfoundation.consumer.dsl.LambdaDsl.newJsonBody;
import static org.assertj.core.api.Assertions.assertThat;

public class BasicOkTest extends AbstractPactTest {

    @Override
    public RequestResponsePact createPact(PactDslWithProvider builder) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", MediaType.APPLICATION_JSON_VALUE);

        return builder
                .given("Two entries exist")
                .uponReceiving("Two entries exist")
                .path("/information")
                .query("name=" + MESSI)
                .method("GET")
                .willRespondWith()
                .headers(headers)
                .status(200)
                .body(newJsonBody((root) -> {
                    root.numberType("salary", 7500);
                    root.stringType("name", MESSI);
                    root.stringType("nationality", "Argentina");
                }).build())
                .toPact();
    }

    @Override
    protected void runTest(MockServer mockServer, PactTestExecutionContext context) {
        providerService.overrideBackendUrl(mockServer.getUrl());
        Information information = providerService.getResponseForName(MESSI).getBody();
        assertThat(information).isNotNull();
        assertThat(information.getName()).isEqualTo(MESSI);
    }

}
