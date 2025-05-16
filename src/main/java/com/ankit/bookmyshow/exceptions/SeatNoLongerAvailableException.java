package com.ankit.bookmyshow.exceptions;

public class SeatNoLongerAvailableException extends RuntimeException {
    public SeatNoLongerAvailableException(String message) {
        super(message);
    }
}
