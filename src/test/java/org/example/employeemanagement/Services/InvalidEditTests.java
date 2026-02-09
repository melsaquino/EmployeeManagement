package org.example.employeemanagement.Services;

import org.example.employeemanagement.Entities.Employee;
import org.example.employeemanagement.Exceptions.EmployeeDoesNotExistException;
import org.example.employeemanagement.Exceptions.InvalidBirthDateException;
import org.example.employeemanagement.Exceptions.InvalidSalaryException;
import org.example.employeemanagement.Repositories.EmployeesRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test Class primarily tests the invalid inputs of editing a user in the service level
 * */
@SpringBootTest
public class InvalidEditTests {

    @Autowired
    EmployeesRepository employeesRepository;
    @Autowired
    ModifyDBServices modifyDBServices;

    /**
     * Tests that the correct exception is thrown when an invalid birthday is entered when editing a user
     * */
    @Test
    public void testInvalidBdayEdit(){
        Employee beforeEmployee = employeesRepository.findByEmployeeId(104);

        Exception exception = assertThrows(InvalidBirthDateException.class, () -> {
            modifyDBServices.updateEmployees(104,"Maria Santo", LocalDate.parse("1100-01-09"),"Accounting",34000);

        });
        assertEquals("Invalid date of birth", exception.getMessage());

        Employee editedEmployee = employeesRepository.findByEmployeeId(104);
        assertEquals(beforeEmployee.getName(),editedEmployee.getName());
        assertEquals(beforeEmployee.getDateOfBirth(),editedEmployee.getDateOfBirth());
        assertEquals(beforeEmployee.getDepartment(),editedEmployee.getDepartment());
        assertEquals(beforeEmployee.getSalary(),editedEmployee.getSalary());
        assertNotEquals(LocalDate.parse("1100-01-09"), editedEmployee.getDateOfBirth());

    }
    /**
     * Tests that the correct exception is thrown when an invalid salary is entered when editing a user
     * */
    @Test
    public void testInvalidSalaryEdit(){
        Employee beforeEmployee = employeesRepository.findByEmployeeId(104);

        Exception exception = assertThrows(InvalidSalaryException.class, () -> {
            modifyDBServices.updateEmployees(104,"Maria Santo", LocalDate.parse("1990-01-09"),"Accounting",-34000);
        });
        assertEquals("Invalid Salary", exception.getMessage());
        Employee editedEmployee = employeesRepository.findByEmployeeId(104);
        assertEquals(beforeEmployee.getName(),editedEmployee.getName());
        assertEquals(beforeEmployee.getDateOfBirth(),editedEmployee.getDateOfBirth());
        assertEquals(beforeEmployee.getDepartment(),editedEmployee.getDepartment());
        assertEquals(beforeEmployee.getSalary(),editedEmployee.getSalary());

        assertNotEquals(-34000, editedEmployee.getSalary());

    }
    /**
     * Tests that the correct exception is thrown when a non-existing user is being edited
     * */
    @Test
    public void testUserDoesNotExistEdit(){

        Exception exception = assertThrows(EmployeeDoesNotExistException.class, () -> {
            // Code that is expected to throw an IllegalArgumentException
            modifyDBServices.updateEmployees(200,"Maria Santo", LocalDate.parse("1990-01-09"),"Accounting",34000);

        });
        assertEquals("User with ID 200 does not exist", exception.getMessage());

    }
}
