package com.example.Gruppuppgift_Statsbibloteket.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class LoanIsNotAvailableException extends RuntimeException {
    public LoanIsNotAvailableException(String message) {
        super(message);
    }

    public LoanIsNotAvailableException(String message, Throwable cause) {
        super(message, cause);
    }
}
