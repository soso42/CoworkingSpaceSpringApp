package org.example.model.exceptions;

public class WorkSpaceNotFoundException extends RuntimeException {
    public WorkSpaceNotFoundException(String message) {
        super(message);
    }
}
