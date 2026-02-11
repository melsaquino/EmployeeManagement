package org.example.employeemanagement.Services;
import org.example.employeemanagement.Entities.Employee;
import org.example.employeemanagement.Repositories.EmployeesRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class DatabaseTests {
    @Autowired
    EmployeesRepository employeesRepository;
    @Autowired
    ModifyDBServices modifyDBServices;
    /**
     * Tests Positive outcomes of adding, editing and deleting a user
     * */
    @Test
    public void testEverythingIsOkay() throws Exception{
        if(modifyDBServices.findByEmployeeId(1007)!=null)
           modifyDBServices.deleteEmployee(1007);

        modifyDBServices.registerUser(1007,"Maria Santos", LocalDate.parse("1991-02-09"),"Accounting",30000);
        Employee employee =employeesRepository.findByEmployeeId(1007);
        assertNotNull(employee);
        assertEquals("Maria Santos", employee.getName());
        assertEquals(LocalDate.parse("1991-02-09"), employee.getDateOfBirth());
        assertEquals("Accounting",employee.getDepartment());
        assertEquals(30000,employee.getSalary());

        //TEST UPDATE
        modifyDBServices.updateEmployees(1007,"Maria Santo", LocalDate.parse("1991-01-09"),"Accounting",34000);
        employee = employeesRepository.findByEmployeeId(1007);
        assertNotNull(employee);

        assertEquals("Maria Santo", employee.getName());
        assertEquals(LocalDate.parse("1991-01-09"), employee.getDateOfBirth());
        assertEquals("Accounting", employee.getDepartment());
        assertEquals(34000, employee.getSalary());

        //TEST DELETE
        modifyDBServices.deleteEmployee(1007);
        employee = employeesRepository.findByEmployeeId(1007);
        assertNull(employee);

    }


}
