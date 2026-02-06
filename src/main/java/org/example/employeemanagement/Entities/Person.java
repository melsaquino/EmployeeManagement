package org.example.employeemanagement.Entities;
import java.time.LocalDate;
import java.time.Period;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
public abstract class Person {
    @Column(nullable = false)
    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    protected String name;

    @Column(nullable = false)
    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    protected LocalDate dateOfBirth;

    public Person(String name, LocalDate dateOfBirth){
        this.name= name;
        this.dateOfBirth =dateOfBirth;
    }
    public Person(){};

    public int getAge(){
        return Period.between( dateOfBirth,LocalDate.now()).getYears();
    }
}
