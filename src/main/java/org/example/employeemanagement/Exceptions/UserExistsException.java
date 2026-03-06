package org.example.employeemanagement.Exceptions;

import java.text.MessageFormat;

/**
 * Custom Exception for when a user already exist
 * */
public class UserExistsException extends RuntimeException {
    public UserExistsException(String message) {
        super(message);
    }
    public UserExistsException(String message, int id){
        super(formatMessage(message,id));
    }
    private static String formatMessage(String message, int id) {
        return MessageFormat.format(message, id);
    }
}
