package com.example.towns;

import com.example.towns.authentication.Credentials;
import com.example.towns.authentication.TokenResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


public class AuthenticateTestSupport {

    @Autowired
    public MockMvc mockMvc;

    public ObjectMapper mapper = new ObjectMapper();

    public String authenticate(String username, String password) {
        try {
            String credentials = mapper.writer().writeValueAsString(new Credentials(username, password));
            MvcResult result = this.mockMvc.perform(post("/authenticate")
                    .contentType("application/json")
                    .content(credentials))
                    .andReturn();
            TokenResponse jwtResp =  mapper.readValue(result.getResponse().getContentAsString(), TokenResponse.class);
            return jwtResp.getToken();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
