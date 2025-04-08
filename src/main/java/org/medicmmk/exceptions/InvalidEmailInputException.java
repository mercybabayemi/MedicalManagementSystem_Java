package org.medicmmk.exceptions;

public class InvalidEmailInputException extends RuntimeException{
    public InvalidEmailInputException(String message) {
        super(message);
    }
}
