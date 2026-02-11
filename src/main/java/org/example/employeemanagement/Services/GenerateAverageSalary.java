package org.example.employeemanagement.Services;

import org.example.employeemanagement.Entities.Employee;
import org.example.employeemanagement.Repositories.EmployeesRepository;

import java.util.List;
import java.util.OptionalDouble;
/**
 * Class that primarily deals with generating average salaries
 * */
public class GenerateAverageSalary extends GenerateService{
    EmployeesRepository employeesRepository;
    public GenerateAverageSalary (EmployeesRepository employeesRepository){
        super();
        this.employeesRepository = employeesRepository;
    }
    /**
     * Class that generates average salary of all employees in the database
     * */
    @Override
    public double generate(){
        double average;
        List<Employee> employees = employeesRepository.findAll();
        average= employees.stream()
                .mapToDouble(Employee::getSalary)
                .average().orElse(0.0);
        return Math.round(average * 100.0) / 100.0;
    }
    /**
     * Class that generates average salary of all employees in a certain department in the database
     * */
    @Override
    public double generate(String department) {
        double average;
        List<Employee> employees = employeesRepository.findByDepartment(department);
        average= employees.stream()
                .mapToDouble(Employee::getSalary)
                .average().orElse(0.0);
        return Math.round(average * 100.0) / 100.0;

    }

}
