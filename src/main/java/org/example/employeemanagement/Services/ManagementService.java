package org.example.employeemanagement.Services;

import jakarta.transaction.Transactional;
import org.example.employeemanagement.DTOs.EmployeeDTO;
import org.example.employeemanagement.Entities.Admin;
import org.example.employeemanagement.Entities.Employee;
import org.example.employeemanagement.Repositories.EmployeesRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import tools.jackson.databind.exc.InvalidFormatException;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ManagementService {
    EmployeesRepository employeesRepository;

    public ManagementService(EmployeesRepository employeesRepository) {
        this.employeesRepository=employeesRepository;
    }

    public List<EmployeeDTO> getAllEmployees(){
        List<Employee> employees = employeesRepository.findAll();
        List<EmployeeDTO> employeesDto = employees.stream()
                .map(EmployeeDTO::new
                ).collect(Collectors.toList());
        return employeesDto;
    }
    @Modifying // Tells Spring Data JPA this is an update/delete operation
    public void updateEmployees(int id,String name, LocalDate dateOfBirth, String department, double salary) throws Exception {
        Employee employee = employeesRepository.findByEmployeeId(id);
        if (employee!=null){
            employee.setName(name);
            employee.setDateOfBirth(dateOfBirth);
            employee.setSalary(salary);
            employee.setDepartment(department);
            employeesRepository.save(employee);
        }else
            throw new Exception("User to update does not exist");
    }
    @Modifying
    @Transactional
    public void deleteEmployee(int employeeId){
        employeesRepository.deleteByEmployeeId(employeeId);
    }

    public EmployeeDTO findByEmployees(String id){
        try{
            return new EmployeeDTO(employeesRepository.findByEmployeeId(Integer.parseInt(id)));
        }catch (InvalidFormatException e){
            throw e;
        }
    }
    public List<EmployeeDTO> findBySearchQuery(String query){
        Integer id=null;
        String name =null;
        List <Employee> employees;
        if (query != null && query.matches("\\d+")) {
            id = Integer.valueOf(query);
            employees = employeesRepository.searchByNameOrEmployeeId(id);
        } else {
            name = query;
            employees = employeesRepository.searchByNameOrEmployeeId(name);

        }
        List<EmployeeDTO> employeesDto = employees.stream()
                .map(org.example.employeemanagement.DTOs.EmployeeDTO::new
                ).collect(Collectors.toList());
        return employeesDto;
    }
    public List <EmployeeDTO> getAllSortedEmployees(String sortBy,String sortOrder) throws Exception {
        List <Employee> employees = employeesRepository.findAll();

        if(sortBy.equalsIgnoreCase("age") && sortOrder.equalsIgnoreCase("ASC")){
            employees.sort(Comparator.comparing(Employee::getAge));
        }else if(sortBy.equalsIgnoreCase("age") && sortOrder.equalsIgnoreCase("DESC")){
            employees.sort(Comparator.comparing(Employee::getAge).reversed());
        }else if(sortBy.equalsIgnoreCase("department") && sortOrder.equalsIgnoreCase("ASC")){
            employees.sort(Comparator.comparing(Employee::getDepartment));
        }else if(sortBy.equalsIgnoreCase("department") && sortOrder.equalsIgnoreCase("DESC")){
            employees.sort(Comparator.comparing(Employee::getDepartment).reversed());
        }else{
            throw new Exception("Invalid sorting");
        }
        List<EmployeeDTO> employeesDto = employees.stream()
                .map(org.example.employeemanagement.DTOs.EmployeeDTO::new
                ).collect(Collectors.toList());
        return employeesDto;
    }
    public void registerUser(int id, String name, LocalDate dateOfBirth, String department, double salary) throws Exception{
        if (name.isEmpty() || department.isEmpty() || dateOfBirth ==null)
            throw new Exception("Empty inputs");
        if(employeesRepository.findByEmployeeId(id)==null){
            Employee employee=new Employee();
            employee.setName(name);
            employee.setDateOfBirth(dateOfBirth);
            employee.setDepartment(department);
            employee.setSalary(salary);
            employee.setEmployeeId(id);

            this.employeesRepository.save(employee);
        }
        else throw new Exception("User already exists!");
    }


}
