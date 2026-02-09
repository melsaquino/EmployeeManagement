package org.example.employeemanagement.Exceptions;
/**
 * Custom Exception for when a user already exist
 * */
public class UserExistsException extends RuntimeException {
    public UserExistsException(String message) {
        super(message);
    }
}
