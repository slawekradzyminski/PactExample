package com.awesome.testing.controller;

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

import javax.naming.ConfigurationException;
import java.util.HashMap;
import java.util.List;

import static com.awesome.testing.data.DatabaseData.MESSI_DB_ENTRY;
import static com.awesome.testing.data.DatabaseData.RONALDO_DB_ENTRY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
        String slawomir = "Slawomir";
        String poland = "Poland";
        int salary = 1;
        Information information = new Information(slawomir, poland, salary);
        String jsonString = objectMapper.writeValueAsString(information);

        mockMvc.perform(post("/information")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value(slawomir))
                .andExpect(jsonPath("$.salary").value(salary))
                .andExpect(jsonPath("$.nationality").value(poland));

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

    @Test
    public void shouldSuccessfullyUpdateViaPut() throws Exception {
        long existingId = getExistingId();

        String name = "Yoda";
        String nationality = "France";
        int salary = 2;

        Information informationUpdate = new Information(name, nationality, salary);
        String jsonString = objectMapper.writeValueAsString(informationUpdate);

        mockMvc.perform(put("/information/" + existingId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.salary").value(salary))
                .andExpect(jsonPath("$.nationality").value(nationality));

        assertThat(informationService.getAllInformation()).hasSize(2);

        Information updatedInformation = informationService.getInformationById(existingId).get();
        assertThat(updatedInformation.getName()).isEqualTo(name);
        assertThat(updatedInformation.getNationality()).isEqualTo(nationality);
        assertThat(updatedInformation.getSalary()).isEqualTo(salary);
    }

    @Test
    public void shouldReturn406WhenNonexistingIdViaPut() throws Exception {
        String name = "Yoda";
        String nationality = "France";
        int salary = 2;

        Information informationToUpdate = new Information(name, nationality, salary);
        String jsonString = objectMapper.writeValueAsString(informationToUpdate);

        mockMvc.perform(put("/information/66666")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString))
                .andExpect(status().isNotAcceptable());
    }

    @Test
    public void shouldSuccessfullyUpdateViaPatch() throws Exception {
        long existingId = getExistingId();
        String name = "Yoda";
        int salary = 2;

        HashMap<String, Object> sampleUpdates = new HashMap<>();
        sampleUpdates.put("name", name);
        sampleUpdates.put("salary", salary);

        String jsonString = objectMapper.writeValueAsString(sampleUpdates);

        mockMvc.perform(patch("/information/" + existingId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.salary").value(salary))
                .andExpect(jsonPath("$.nationality").value(RONALDO_DB_ENTRY.getNationality()));

        assertThat(informationService.getAllInformation()).hasSize(2);

        Information updatedInformation = informationService.getInformationById(existingId).get();
        assertThat(updatedInformation.getName()).isEqualTo(name);
        assertThat(updatedInformation.getSalary()).isEqualTo(salary);
        assertThat(updatedInformation.getNationality()).isEqualTo(RONALDO_DB_ENTRY.getNationality());
    }

    @Test
    public void shouldReturn406WhenNonexistingIdViaPatch() throws Exception {
        HashMap<String, Object> sampleUpdates = new HashMap<>();
        sampleUpdates.put("key", "value");

        String jsonString = objectMapper.writeValueAsString(sampleUpdates);

        mockMvc.perform(patch("/information/66666")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString))
                .andExpect(status().isNotAcceptable());
    }

    @Test
    public void shouldReturn400WhenNonexistingParameterViaPatch() throws Exception {
        long existingId = getExistingId();

        HashMap<String, Object> sampleUpdates = new HashMap<>();
        sampleUpdates.put("key", "value");

        String jsonString = objectMapper.writeValueAsString(sampleUpdates);

        mockMvc.perform(patch("/information/" + existingId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(
                        "parameter that does not exist in Information DTO")));
    }

    private long getExistingId() throws ConfigurationException {
        return informationService.getAllInformation()
                .stream()
                .map(Information::getId)
                .findFirst()
                .orElseThrow(ConfigurationException::new);
    }

}
