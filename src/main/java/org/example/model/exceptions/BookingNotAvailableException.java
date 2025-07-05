package org.example.model.exceptions;

public class BookingNotAvailableException extends RuntimeException {
    public BookingNotAvailableException(String message) {
        super(message);
    }
}
