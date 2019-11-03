package com.awesome.testing.wiremock;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.awesome.testing.controller.RootController.RONALDO;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RootControllerProxyErrorTest extends AbstractWiremock {

    @Before
    public void prepareBackendResponse() {
        stubFor(get(urlEqualTo("/information?name=" + RONALDO))
                .willReturn(aResponse()
                        .withStatus(404)));
    }

    @Test
    public void shouldCorrectlyRespond() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(status().isNotFound());
    }

}
