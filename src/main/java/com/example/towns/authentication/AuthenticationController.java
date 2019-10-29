package com.example.towns.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

    @Autowired
    AuthenticationService authenticationService;

    @PostMapping(value = "/authenticate")
    public TokenResponse authenticate(@RequestBody Credentials credentials) throws Exception {
        return authenticationService.authenticate(credentials);
    }

    @PostMapping(value = "/register")
    public TokenResponse register(@RequestBody Credentials credentials) throws Exception {
        return authenticationService.register(credentials);
    }

}
