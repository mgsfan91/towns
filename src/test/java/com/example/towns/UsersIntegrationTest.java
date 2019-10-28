package com.example.towns;

import com.example.towns.configuration.JwtRequest;
import com.example.towns.configuration.JwtResponse;
import com.example.towns.town.Town;
import com.example.towns.town.TownRepository;
import com.example.towns.user.User;
import com.example.towns.user.UserRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UsersIntegrationTest extends AuthenticateTestSupport {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    TownRepository townRepository;

    @Autowired
    UserRepository userRepository;

    @Before
    public void initData() {
        Town t1 = new Town(0, "Dubrovnik", "biser Jadrana", 40000, 0);
        Town t2 = new Town(0, "Vukovar", "na Dunavu", 30000, 5);
        Town t3 = new Town(0, "Rijeka", "najveca luka u RH", 120000, 3);
        this.townRepository.save(t1);
        this.townRepository.save(t2);
        this.townRepository.save(t3);

        User u1 = new User("test", "test", (short)1, null);
        this.userRepository.save(u1);
    }

    @After
    public void clearData() {
        this.townRepository.deleteAll();
        this.userRepository.deleteAll();
    }


    @Test
    public void whenAddedTown_shouldOkAndHaveItListed() throws Exception {

        String token = authenticate("test", "test");

        String newTown = mapper.writer().writeValueAsString(new Town(0, "Split", "najveci dalmatinski", 200000, 0));
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
    public void whenAddedToFavouriteTowns_shouldOkAndHaveItListed() throws Exception {

        String token = authenticate("test", "test");

        // get an id of a town
        MvcResult result = mockMvc.perform(get("/towns/byCreated").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andReturn();
        List towns =  mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<Town>>(){});
        Integer id = ((Town) towns.get(0)).getId();

        // add the town to favourites
        mockMvc.perform(post("/users/towns/" + id)
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // get users and assert it is there
//        String newTown = mapper.writer().writeValueAsString(new Town(0, "Split", "najveci dalmatinski", 200000, 0));
//        mockMvc.perform(post("/users/town")
//                .header("Authorization", "Bearer " + token)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(newTown))
//                .andExpect(status().isOk());
//
//        mockMvc.perform(get("/towns/byCreated").contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", hasSize(4)))
//                .andExpect(jsonPath("$[3].name", is("Split")));
    }



}
