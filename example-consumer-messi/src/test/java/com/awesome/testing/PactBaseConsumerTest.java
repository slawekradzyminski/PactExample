package com.awesome.testing;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.PactTestExecutionContext;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit.ConsumerPactTest;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

import static io.pactfoundation.consumer.dsl.LambdaDsl.newJsonBody;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PactBaseConsumerTest extends ConsumerPactTest {

    @Autowired
    ProviderService providerService;

    @Override
    protected String providerName() {
        return "ExampleProvider";
    }

    @Override
    protected String consumerName() {
        return "Messi";
    }

    @Override
    @Pact(provider = "ExampleProvider", consumer = "Messi")
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
                    root.numberType("salary");
                    root.stringType("name", "Leo Messi");
                    root.stringType("nationality", "Argentina");
                    root.object("contact", (contactObject) -> {
                        contactObject.stringMatcher("Email", ".*@messi.com", "leo@messi.com");
                        contactObject.stringType("Phone Number", "9090940");
                    });
                }).build())
                .toPact();
    }

    @Override
    protected void runTest(MockServer mockServer, PactTestExecutionContext context) {
        providerService.overrideBackendUrl(mockServer.getUrl());
        Information information = providerService.getInformation();
        assertEquals(information.getName(), "Leo Messi");
    }

}
