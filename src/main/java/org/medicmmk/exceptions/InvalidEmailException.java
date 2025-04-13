package org.medicmmk.exceptions;

public class InvalidEmailException extends RuntimeException {
    public InvalidEmailException(String invalidCredentials) {
        super(invalidCredentials);
    }
}
