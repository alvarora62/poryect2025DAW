package com.daw.quickShip.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when a format validation fails.
 *
 * <p>This exception results in a 400 Bad Request HTTP response.</p>
 *
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class FormatException extends RuntimeException {
    public FormatException(String message) {
        super(message);
    }
}
