package org.example.employeemanagement.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
/**
 * Entity meant to represent the admin
 * */
@Entity
@Table(name="admins")
public class Admin extends Employee{
    @Column(nullable = false)
    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    private String password;
    public Admin(){
        super();
    }
}
