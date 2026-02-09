package org.example.employeemanagement.Services;

import jakarta.transaction.Transactional;
import org.example.employeemanagement.DTOs.EmployeeDTO;
import org.example.employeemanagement.Entities.Employee;
import org.example.employeemanagement.Repositories.EmployeesRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
/**
 * Service that deals with other mangement actions that won't modify the database
 * */
@Service
public class ManagementService {
    EmployeesRepository employeesRepository;

    public ManagementService(EmployeesRepository employeesRepository) {
        this.employeesRepository=employeesRepository;
    }
    /**
     * Gets all employees in the employees table
     * @return a list of EmployeeDTO that represents all employees in the database
     * */
    public List<EmployeeDTO> getAllEmployees(){
        List<Employee> employees = employeesRepository.findAll();
        List<EmployeeDTO> employeesDto = employees.stream()
                .map(EmployeeDTO::new
                ).collect(Collectors.toList());
        return employeesDto;
    }
    /**
     * Gets all the employees based on the user's search query
     * @param query the query the user entered in the search bar
     * @return a lists of EmployeeDTO that somehow match the use query
     * */
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
    /**
     * sorts all employees based on certain criteria
     * @param sortBy what should be sorted
     * @param sortOrder the order the list will be sorted
     * @return a list of EmployeeDTO that are sorted based on the user's criteria
     * */
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
    /**
     * Method that gets all employees that have the same department
     * @param department  the department being filtered
     * @return a list of EmployeeDTO that would be shown to the user
     * */
    public List<EmployeeDTO> getFilteredEmployees(String department) {
        List<Employee> employees = new ArrayList<>();

        if ((department==null||department.isEmpty()))
            return getAllEmployees();
        else{
            employees= this.employeesRepository.findByDepartment(department);
        }

        List<EmployeeDTO> employeesDto = employees.stream()
                .map(org.example.employeemanagement.DTOs.EmployeeDTO::new
                ).collect(Collectors.toList());
        return employeesDto;

    }

    /**
     * Returns an employeeDTO that is being queried
     * @param userId the userID of the employee being searched
     * */
    public EmployeeDTO findByEmployees(String userId) {
        return new EmployeeDTO(employeesRepository.findByEmployeeId(Integer.parseInt(userId)));
    }
}
