package com.example.towns;


import io.jsonwebtoken.Jwts;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Sql(scripts = "classpath:data-test-h2.sql")
public class AuthenticationIntegrationTest extends AuthenticateTestSupport  {


    @Test
    public void whenGoodCredentials_shouldProvideToken() throws Exception {
        String token = authenticate("test", "test");
        Jwts.parser()
                .requireSubject("test")
                .setSigningKey("secret".getBytes("UTF-8"))
                .parseClaimsJws(token);
    }

    @Test
    public void whenBadCredentials_shouldNotProvideToken() throws Exception {
        String token = authenticate("test1", "test");
        assertNull(token);
    }
}
