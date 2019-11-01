package com.awesome.testing.wiremock;

import com.awesome.testing.Information;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.awesome.testing.controller.RootController.RONALDO;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RootControllerTest extends AbstractWiremock {

    private static final String PORTUGAL = "Portugal";
    private static final int SALARY = 666;

    @Before
    public void prepareBackend() throws JsonProcessingException {
        Information ronaldo = new Information(RONALDO, PORTUGAL, SALARY);
        String jsonBody = objectMapper.writeValueAsString(ronaldo);

        stubFor(get(urlEqualTo("/information?name=" + RONALDO))
                .willReturn(aResponse()
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(jsonBody)));
    }

    @Test
    public void shouldCorrectlyRespond() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(RONALDO))
                .andExpect(jsonPath("$.nationality").value(PORTUGAL))
                .andExpect(jsonPath("$.salary").value(SALARY));
    }

}
