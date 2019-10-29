package com.example.towns;

import com.example.towns.town.Town;
import com.example.towns.town.TownRepository;
import com.example.towns.user.UserRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import static org.junit.Assert.*;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Sql(scripts = "classpath:data-test-h2.sql")
public class UsersIntegrationTest extends AuthenticateTestSupport {

    @Autowired
    TownRepository townRepository;

    @Autowired
    UserRepository userRepository;


    @Test
    public void whenAddedTown_shouldHaveItListed() throws Exception {

        String token = authenticate("test", "test");

        String newTown = mapper.writer().writeValueAsString(new Town(0, "Split", "najveci dalmatinski", 200000, 0, "test"));
        mockMvc.perform(post("/users/town")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(newTown))
        .andExpect(status().isOk());

        mockMvc.perform(get("/towns/byCreated").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$[3].name", is("Split")));
    }

    @Test
    public void whenAddedToFavourites_shouldHaveIt() throws Exception {

        String token = authenticate("test", "test");

        // add Zagreb(id=1) to favourites
        mockMvc.perform(post("/users/towns/1")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // get users and assert it is there
        MvcResult result = mockMvc.perform(get("/users/towns")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        List towns = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<Town>>(){});
        Town town = (Town) towns.get(0);
        assertEquals((int)town.getId(), 1);
        assertEquals(town.getName(), "Zagreb");
    }

    @Test
    public void whenAddedAndRemovedFromFavourites_shouldHaveRightOnes() throws Exception {

        String token = authenticate("test", "test");

        // add Zagreb(id=1) to favourites
        mockMvc.perform(post("/users/towns/1")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // add Rijeka(id=2) to favourites
        mockMvc.perform(post("/users/towns/2")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // remove Zagreb(id=1) from favourites
        mockMvc.perform(delete("/users/towns/1")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // get users and assert Rijeka is there
        MvcResult result = mockMvc.perform(get("/users/towns")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        List towns = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<Town>>(){});
        Town town = (Town) towns.get(0);
        assertEquals((int)town.getId(), 2);
        assertEquals(town.getName(), "Rijeka");
    }

}
