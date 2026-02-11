package org.example.employeemanagement.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name="hr")
public class HR extends Employee{
    public HR(){
        super();
    }
    public HR(Employee employee){
        this.employeeId =employee.getEmployeeId();
        this.name =employee.getName();
        this.department ="HR";
        this.salary = employee.getSalary();
        this.dateOfBirth =employee.getDateOfBirth();
    }
}
