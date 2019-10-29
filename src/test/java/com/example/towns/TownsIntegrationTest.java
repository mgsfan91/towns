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
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Sql(scripts = "classpath:data-test-h2.sql")
public class TownsIntegrationTest extends AuthenticateTestSupport {

    @Autowired
    TownRepository townRepository;

    @Autowired
    UserRepository userRepository;


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
        String token = authenticate("test", "test");

        // add Rijeka(id=2) to favourites
        mockMvc.perform(post("/users/towns/2")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // Rijeka is the most popular now
        mockMvc.perform(get("/towns/byPopularity").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].name", is("Rijeka")));
    }
}
