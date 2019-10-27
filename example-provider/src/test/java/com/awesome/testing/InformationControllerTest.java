package com.awesome.testing;

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
import com.awesome.testing.dto.Information;

import java.util.List;

import static com.awesome.testing.data.DatabaseData.MESSI_DB_ENTRY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

    @Before
    public void resetDatabase() {
        ContractState.DEFAULT.setState(informationService);
    }

    @Test
    public void shouldReturnAll() throws Exception {
        mockMvc.perform(get("/information"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void shouldReturnByName() throws Exception {
        mockMvc.perform(get("/information?name=LeoMessi"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(MESSI_DB_ENTRY.getName()))
                .andExpect(jsonPath("$.salary").value(MESSI_DB_ENTRY.getSalary()))
                .andExpect(jsonPath("$.nationality").value(MESSI_DB_ENTRY.getNationality()));
    }

    @Test
    public void shouldReturnById() throws Exception {
        String name = "Yoda";
        String nationality = "France";
        int salary = 2;

        Information informationToAdd = new Information(name, nationality, salary);
        Information informationAdded = informationService.save(informationToAdd);

        mockMvc.perform(get("/information/" + informationAdded.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.salary").value(salary))
                .andExpect(jsonPath("$.nationality").value(nationality));
    }

    @Test
    public void shouldReturn404ForNonexsistingName() throws Exception {
        mockMvc.perform(get("/information?name=Nonexisting"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturn404ForNonexsistingId() throws Exception {
        mockMvc.perform(get("/information/6666"))
                .andExpect(status().isNotFound());
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

    @Test
    public void shouldReturn422WhenPostingExistingName() throws Exception {
        Information information = new Information(MESSI_DB_ENTRY.getName(), "Poland", 1);
        String jsonString = objectMapper.writeValueAsString(information);

        mockMvc.perform(post("/information")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void shouldSuccessfullyDelete() throws Exception {
        String name = "Yoda";
        String nationality = "France";
        int salary = 2;

        Information informationToAdd = new Information(name, nationality, salary);
        Information informationAdded = informationService.save(informationToAdd);

        mockMvc.perform(delete("/information/" + informationAdded.getId()))
                .andExpect(status().isNoContent());
    }

}
