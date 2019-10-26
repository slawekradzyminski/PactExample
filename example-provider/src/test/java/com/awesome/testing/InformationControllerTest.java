package com.awesome.testing;

import com.awesome.testing.service.InformationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import com.awesome.testing.dto.Information;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class InformationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private InformationService informationService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void shouldReturnAll() throws Exception {
        mockMvc.perform(get("/information"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void shouldSuccessfullyAdd() throws Exception {
        Information information = new Information("Slawomir", "Poland", 1);
        String jsonString = objectMapper.writeValueAsString(information);

        mockMvc.perform(post("/information")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString))
                .andExpect(status().isCreated());

        List<Information> allInformation = informationService.getAllInformation();
        assertThat(allInformation).hasSize(3);
    }

}
