package com.example.database.towns.setup;

public class TownNotFoundException extends RuntimeException {

    public TownNotFoundException(String message) {
        super(message);
    }
}
