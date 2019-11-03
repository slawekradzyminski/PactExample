package com.awesome.testing.wiremock;

import com.awesome.testing.dto.information.IdNotAwareInformation;
import com.awesome.testing.dto.information.Information;
import com.awesome.testing.dto.information.TimedInformation;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.Instant;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.put;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UpdateControllerTest extends AbstractWiremock {

    @Before
    public void prepareBackendResponse() throws JsonProcessingException {
        Information sample = new Information(ID, NAME, NATIONALITY, SALARY);
        String jsonBody = objectMapper.writeValueAsString(sample);

        stubFor(put(urlEqualTo("/information/1"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(jsonBody)));
    }

    @Test
    public void shouldCorrectlyRespond() throws Exception {
        IdNotAwareInformation info = new IdNotAwareInformation(NAME, NATIONALITY, SALARY);
        String jsonBody = objectMapper.writeValueAsString(info);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(ID))
                .andExpect(jsonPath("$.name").value(NAME))
                .andExpect(jsonPath("$.nationality").value(NATIONALITY))
                .andExpect(jsonPath("$.salary").value(SALARY))
                .andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();

        TimedInformation timedInformation = objectMapper.readValue(contentAsString, TimedInformation.class);
        assertThat(timedInformation.getTimestamp()).isBefore(Instant.now());
    }

}
