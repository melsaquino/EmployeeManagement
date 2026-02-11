package org.example.employeemanagement.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name="accounting")
public class Accounting extends Employee{
    public Accounting(){
        super();
    }
    public Accounting(Employee employee){
        this.employeeId =employee.getEmployeeId();
        this.name =employee.getName();
        this.department ="Accounting";
        this.salary = employee.getSalary();
        this.dateOfBirth =employee.getDateOfBirth();
    }
}
