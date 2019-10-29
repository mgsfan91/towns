package com.example.database.towns.authentication;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Credentials {

    private String username;
    private String password;

    @Override
    public String toString() {
        return "Credentials[username: " + username + ", password: <hidden>]";
    }
}
