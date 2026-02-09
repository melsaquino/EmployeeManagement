package org.example.employeemanagement.Exceptions;
/**
 * Custom Exception for when an entered salary is invalid
 * */
public class InvalidSalaryException extends Exception{
    public InvalidSalaryException(String message){
        super(message);
    }
}
