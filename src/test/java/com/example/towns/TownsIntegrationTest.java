package com.example.towns;

import com.example.towns.town.Town;
import com.example.towns.town.TownRepository;
import com.example.towns.user.User;
import com.example.towns.user.UserRepository;
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

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TownsIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    TownRepository townRepository;

    @Autowired
    UserRepository userRepository;

    ObjectMapper mapper = new ObjectMapper();

//    @Before
//    public void initData() {
//        User u1 = new User("test1", "test1", (short)1, null);
//        User u2 = new User("test2", "test2", (short)1, null);
//        User u3 = new User("test3", "test3", (short)1, null);
//        this.userRepository.save(u1);
//        this.userRepository.save(u2);
//        this.userRepository.save(u3);
//    }

    @Before
    public void initData() {
        Town t1 = new Town(0, "Dubrovnik", "biser Jadrana", 40000, 0);
        Town t2 = new Town(0, "Vukovar", "na Dunavu", 30000, 5);
        Town t3 = new Town(0, "Rijeka", "najveca luka u RH", 120000, 3);
        this.townRepository.save(t1);
        this.townRepository.save(t2);
        this.townRepository.save(t3);
    }

    @After
    public void clearData() {
        this.townRepository.deleteAll();
    }


    @Test
    public void requestedTowns_shouldReturnList() throws Exception {
        mockMvc.perform(get("/towns/byCreated").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].name", is("Dubrovnik")))
                .andExpect(jsonPath("$[2].name", is("Rijeka")));
    }

    @Test
    public void requestedTownsByPopularity_shouldReturnList() throws Exception {
        mockMvc.perform(get("/towns/byPopularity").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].name", is("Vukovar")))
                .andExpect(jsonPath("$[2].name", is("Dubrovnik")));
    }
}
