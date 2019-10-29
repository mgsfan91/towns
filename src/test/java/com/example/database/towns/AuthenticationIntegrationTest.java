package com.example.database.towns;

import com.example.database.towns.authentication.Credentials;
import com.example.database.towns.authentication.TokenResponse;
import io.jsonwebtoken.Jwts;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Sql(scripts = "classpath:data-test-h2.sql")
public class AuthenticationIntegrationTest extends AuthenticateTestSupport  {

    @Value("${jwt.secret}")
    private String secret;


    @Test
    public void whenGoodCredentials_shouldProvideValidToken() throws Exception {
        String token = authenticate("test", "test");
        Jwts.parser().requireSubject("test").setSigningKey(secret)
                .parseClaimsJws(token);
    }

    @Test
    public void whenBadCredentials_shouldNotProvideToken() throws Exception {
        String token = authenticate("test1", "test");
        assertNull(token);
    }

    @Test
    public void whenRegisteringUser_shouldProvideValidToken() throws Exception {
        String credentials = mapper.writer().writeValueAsString(new Credentials("test2", "test2"));
        MvcResult result = mockMvc.perform(post("/register")
                .contentType("application/json")
                .content(credentials))
                .andReturn();
        TokenResponse tokenResponse =  mapper.readValue(result.getResponse().getContentAsString(), TokenResponse.class);

        Jwts.parser().requireSubject("test2").setSigningKey(secret)
                .parseClaimsJws(tokenResponse.getToken());
    }
}
