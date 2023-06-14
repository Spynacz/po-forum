package org.forum.services;

public class NoPasswordException extends RuntimeException {
    public NoPasswordException(String message) {
        super(message);
    }
}
