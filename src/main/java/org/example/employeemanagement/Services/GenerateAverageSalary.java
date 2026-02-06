package org.example.employeemanagement.Services;

import org.example.employeemanagement.Entities.Employee;
import org.example.employeemanagement.Repositories.EmployeesRepository;

import java.util.List;

public class GenerateAverageSalary extends GenerateService{
    EmployeesRepository employeesRepository;
    public GenerateAverageSalary (EmployeesRepository employeesRepository){
        super();
        this.employeesRepository = employeesRepository;
    }
    @Override
    public double generate(){
        double average;
        double total=0;
        List<Employee> employees = employeesRepository.findAll();
        int size = employees.size();
        for(Employee employee: employees){
            total += employee.getSalary();
        }
        average = total/size;
        return Math.round(average * 100.0) / 100.0;
    }

}
