package org.example.employeemanagement.Exceptions;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.ResourceBundle;

/**
 * Custom Exception for when an employee does not exist
 * */
public class EmployeeDoesNotExistException extends RuntimeException {
    private final ResourceBundle bundle = ResourceBundle.getBundle("messages-en");

    public EmployeeDoesNotExistException(String message) {
        super(message);
    }

    public EmployeeDoesNotExistException(String message, int id){
        //MessageFormat messageFormat = new MessageFormat(message);
        super(formatMessage(message,id));
    }
    private static String formatMessage(String message, int id) {
        return MessageFormat.format(message, id);
    }
}
