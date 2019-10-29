package com.example.towns.setup;

public class TownNotFoundException extends RuntimeException {

    public TownNotFoundException(String message) {
        super(message);
    }
}
