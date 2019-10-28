package com.example.towns;

import com.example.towns.town.TownRepository;
import com.example.towns.user.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts = "classpath:data-test-h2.sql")
public class TownsIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    TownRepository townRepository;

    @Autowired
    UserRepository userRepository;

    ObjectMapper mapper = new ObjectMapper();


    @Test
    public void requestedTowns_shouldReturnList() throws Exception {
        mockMvc.perform(get("/towns/byCreated").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].name", is("Zagreb")))
                .andExpect(jsonPath("$[2].name", is("Slavonski Brod")));
    }

    @Test
    public void requestedTownsByPopularity_shouldReturnList() throws Exception {
        mockMvc.perform(get("/towns/byPopularity").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].name", is("Rijeka")))
                .andExpect(jsonPath("$[2].name", is("Zagreb")));
    }
}
