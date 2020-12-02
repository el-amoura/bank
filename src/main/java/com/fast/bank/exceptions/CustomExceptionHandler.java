package com.fast.bank.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.bind.ValidationException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice(annotations = {RestController.class})
public class CustomExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(CustomExceptionHandler.class);

    @ExceptionHandler(value = ValidationException.class)
    public ResponseEntity<Object> handleValidationExceptions(ValidationException e) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", e.getMessage());
        logger.warn("Received error: " + e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
