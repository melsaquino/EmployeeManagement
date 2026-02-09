package org.example.employeemanagement.Exceptions;
/**
 * Custom Exception for when an entered date of birth does not exist
 * */
public class InvalidBirthDateException extends RuntimeException {
    public InvalidBirthDateException(String message) {
        super(message);
    }
}
