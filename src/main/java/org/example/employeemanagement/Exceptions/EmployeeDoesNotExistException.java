package org.example.employeemanagement.Exceptions;
/**
 * Custom Exception for when an employee does not exist
 * */
public class EmployeeDoesNotExistException extends RuntimeException {
    public EmployeeDoesNotExistException(String message) {
        super(message);
    }
}
