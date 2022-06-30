package ru.billing.testwork.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MyExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<String> responseEntityException(Exception exception) {
        String status = "Error caused by exception: " + exception.getMessage();
        return new ResponseEntity<>(status, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
