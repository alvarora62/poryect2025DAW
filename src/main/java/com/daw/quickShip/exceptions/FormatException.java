package com.daw.quickShip.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class FormatException extends RuntimeException {
    public FormatException(String message) {
        super(message);
    }
}
