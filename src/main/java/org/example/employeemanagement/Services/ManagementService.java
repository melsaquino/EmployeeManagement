package org.example.employeemanagement.Services;

import jakarta.transaction.Transactional;
import org.example.employeemanagement.DTOs.EmployeeDTO;
import org.example.employeemanagement.Entities.Employee;
import org.example.employeemanagement.Repositories.EmployeesRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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

}
