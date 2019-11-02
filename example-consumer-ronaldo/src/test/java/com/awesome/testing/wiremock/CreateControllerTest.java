package com.awesome.testing.wiremock;

import com.awesome.testing.dto.Information;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CreateControllerTest extends AbstractWiremock {

    private static final String NAME = "SampleName";
    private static final String NATIONALITY = "Gabon";
    private static final int SALARY = 1234;

    private String jsonBody;

    @Before
    public void prepareBackend() throws JsonProcessingException {
        Information sample = new Information(NAME, NATIONALITY, SALARY);
        jsonBody = objectMapper.writeValueAsString(sample);

        stubFor(post(urlEqualTo("/information"))
                .willReturn(aResponse()
                        .withStatus(201)
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(jsonBody)));
    }

    @Test
    public void shouldCorrectlyRespond() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody))
                .andExpect(status().isCreated());
    }

}
