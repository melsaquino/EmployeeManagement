package org.example.employeemanagement.DTOs;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.example.employeemanagement.Entities.Employee;

import java.time.LocalDate;

public class EmployeeDTO {

    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    protected int employeeId;

    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    protected String name;

    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    protected String department;

    @Column(nullable = false)
    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    protected LocalDate dateOfBirth;

    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    protected double salary;
    public EmployeeDTO(Employee employee){
        this.name = employee.getName();
        this.employeeId = employee.getEmployeeId();
        this.department = employee.getDepartment();
        this.salary =employee.getSalary();
        this.dateOfBirth = employee.getDateOfBirth();
    }
}
