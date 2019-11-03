package com.awesome.testing.controller;

import com.awesome.testing.dto.Information;
import com.awesome.testing.dto.StateDto;
import com.awesome.testing.service.InformationService;
import com.awesome.testing.state.ContractState;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class PactControllerTest {

    private static final String SLAWOMIR = "Slawomir";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private InformationService informationService;

    @Autowired
    private ObjectMapper objectMapper;

    @Before
    public void setUp() {
        ContractState.EMPTY.setState(informationService);
        Information testInfo = new Information(SLAWOMIR, "Poland", 1);
        informationService.save(testInfo);
        assertThat(informationService.getAllInformation()).hasSize(1);
    }

    @Test
    public void shouldEmptyDatabase() throws Exception {
        StateDto emptyDbState = new StateDto(ContractState.EMPTY.description());
        String jsonString = objectMapper.writeValueAsString(emptyDbState);
        mockMvc.perform(post("/pactStateChange")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString))
                .andExpect(status().is2xxSuccessful());

        assertThat(informationService.getAllInformation()).hasSize(0);
    }

    @Test
    public void shouldAddRecords() throws Exception {
        StateDto defaultState = new StateDto(ContractState.DEFAULT.description());
        String jsonString = objectMapper.writeValueAsString(defaultState);
        mockMvc.perform(post("/pactStateChange")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString))
                .andExpect(status().is2xxSuccessful());

        Optional<Information> slawomir =
                informationService.getAllInformation()
                .stream()
                .filter(information -> information.getName().equals(SLAWOMIR))
                .findFirst();
        assertThat(slawomir).isEmpty();

        assertThat(informationService.getAllInformation()).hasSizeGreaterThan(0);
    }
}
