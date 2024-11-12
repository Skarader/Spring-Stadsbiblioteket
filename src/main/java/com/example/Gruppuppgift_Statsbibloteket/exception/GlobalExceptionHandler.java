package com.example.Gruppuppgift_Statsbibloteket.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(BookNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleBookNotFoundException(BookNotFoundException ex) {
    ErrorResponse errorResponse = new ErrorResponse(
            "Book not found",
            ex.getMessage(),
            System.currentTimeMillis()
    );

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
  }
}
