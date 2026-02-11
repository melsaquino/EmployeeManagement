package org.example.employeemanagement.Services;

import org.example.employeemanagement.Entities.Employee;
import org.example.employeemanagement.Repositories.EmployeesRepository;

import java.util.List;
/**
 * Class that primarily generates average age
 * */
public class GenerateAverageAge extends GenerateService {
    private EmployeesRepository employeesRepository;
    public GenerateAverageAge (EmployeesRepository employeesRepository){
        super();
        this.employeesRepository = employeesRepository;
    }
    /**
     * Class that generates average age of all employees in the database
     * */
    @Override
    public double generate(){
        double average;
        List<Employee> employees = employeesRepository.findAll();
        average= employees.stream()
                .mapToDouble(Employee::getAge) // Convert Integer objects to primitive int stream
                .average().orElse(0.0);
        return   Math.round(average * 100.0) / 100.0;
    }
    /**
     * Class that generates average age of all employees in a certain department in the database
     * */
    @Override
    public double generate(String department){
        double average;
        List<Employee> employees = employeesRepository.findByDepartment(department);
        average= employees.stream()
                .mapToDouble(Employee::getAge) // Convert Integer objects to primitive int stream
                .average().orElse(0.0);
        return   Math.round(average * 100.0) / 100.0;

    }

}
