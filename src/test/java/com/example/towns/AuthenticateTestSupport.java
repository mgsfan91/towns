package com.example.towns;

import com.example.towns.configuration.JwtRequest;
import com.example.towns.configuration.JwtResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AuthenticateTestSupport {

    @Autowired
    private MockMvc mockMvc;

    public ObjectMapper mapper = new ObjectMapper();

    public String authenticate(String username, String password) {

        try {
            String credentials = mapper.writer().writeValueAsString(new JwtRequest(username, password));
            MvcResult result = this.mockMvc.perform(post("/authenticate")
                    .contentType("application/json")
                    .content(credentials))
                    .andExpect(status().isOk())
                    .andReturn();
            JwtResponse jwtResp =  mapper.readValue(result.getResponse().getContentAsString(), JwtResponse.class);
            return jwtResp.getToken();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
