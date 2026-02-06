package org.example.employeemanagement.Entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name="employees")
public class Employee extends Person{
    @Id
    @Column(nullable=false)
    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    protected int employeeId;

    @Column(nullable = false)
    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    protected String department;

    @Column(nullable = false)
    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    protected double salary;

    @Column(nullable = false)
    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    private String password;
    public Employee(String name, LocalDate dateOfBirth){
        super(name,dateOfBirth);
    }

    public Employee(){
        super();
    };

}
