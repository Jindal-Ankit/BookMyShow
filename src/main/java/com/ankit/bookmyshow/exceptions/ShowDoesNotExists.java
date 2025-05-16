package com.ankit.bookmyshow.exceptions;

public class ShowDoesNotExists extends RuntimeException {
    public ShowDoesNotExists(String message) {
        super(message);
    }
}
