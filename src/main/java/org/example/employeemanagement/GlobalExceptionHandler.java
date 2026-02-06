package org.example.employeemanagement;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Catch specific MethodArgumentTypeMismatchException
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST) // Return 400 Bad Request
    @ResponseBody
    public String handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String error = "Parameter '" + ex.getName() + "' should be of type '" +
                ex.getRequiredType().getSimpleName() + "'";
        return "{\"error\": \"" + error + "\"}";
    }
}