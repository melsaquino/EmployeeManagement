package org.example.employeemanagement.Services;

import org.example.employeemanagement.Entities.Employee;
import org.example.employeemanagement.Exceptions.InvalidBirthDateException;
import org.example.employeemanagement.Exceptions.InvalidSalaryException;
import org.example.employeemanagement.Exceptions.UserExistsException;
import org.example.employeemanagement.Repositories.EmployeesRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
/**
 * Class that primarily test the invalid outputs of adding Employees
 * */
@SpringBootTest
public class InvalidCreateUserTests {
    @Autowired
    EmployeesRepository employeesRepository;
    @Autowired
    ModifyDBServices modifyDBServices;

    /**
     * Method tests invalid birthday input in adding an employees and checks that the correct exception and exception message is thrown
     * */
    @Test
    public void testInvalidBdayAdd(){
        Employee beforeEmployee = employeesRepository.findByEmployeeId(104);

        Exception exception = assertThrows(InvalidBirthDateException.class, () -> {
            modifyDBServices.registerUser(108,"Maria Santo", LocalDate.parse("1100-01-09"),"Accounting",34000);

        });
        assertEquals("Invalid date of birth", exception.getMessage());

    }
    /**
     * Method tests invalid salary input in adding an employees and checks that the correct exception and exception message is thrown
     * */
    @Test
    public void testInvalidSalaryAdd(){

        Exception exception = assertThrows(InvalidSalaryException.class, () -> {
            modifyDBServices.registerUser(109,"Maria Cruz", LocalDate.parse("1990-01-09"),"Accounting",-34000);
        });
        assertEquals("Invalid Salary", exception.getMessage());


    }
    /**
     * Method tests that when an employee with an existing ID is added the correct exception is thrown with the correct message
     * */
    @Test
    public void testEmployeeExistEdit(){
        Employee beforeEmployee = employeesRepository.findByEmployeeId(104);

        Exception exception = assertThrows(UserExistsException.class, () -> {
            // Code that is expected to throw an IllegalArgumentException
            modifyDBServices.registerUser(100,"Maria Santo", LocalDate.parse("1990-01-09"),"Accounting",34000);

        });
        assertEquals("User with ID 100 already exists!", exception.getMessage());

    }
}
