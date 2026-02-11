package org.example.employeemanagement.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name="it")
public class IT extends Employee{
    public IT(){
        super();
    }
    public IT(Employee employee){
        this.employeeId =employee.getEmployeeId();
        this.name =employee.getName();
        this.department ="IT";
        this.salary = employee.getSalary();
        this.dateOfBirth =employee.getDateOfBirth();
    }
}
