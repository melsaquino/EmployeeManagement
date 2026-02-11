package org.example.employeemanagement.Services;

import jakarta.transaction.Transactional;
import org.example.employeemanagement.DTOs.EmployeeDTO;
import org.example.employeemanagement.Entities.Employee;
import org.example.employeemanagement.Repositories.EmployeesRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
/**
 * Service that deals with other management actions that won't modify the database
 * */
@Service
public class ManagementService {
    private EmployeesRepository employeesRepository;

    public ManagementService(EmployeesRepository employeesRepository) {
        this.employeesRepository=employeesRepository;
    }
    /**
     * Gets all employees in the employees table
     * @return a list of EmployeeDTO that represents all employees in the database
     * */
    public List<EmployeeDTO> getAllEmployees(int page, int size){
        Pageable pageable = PageRequest.of(page, size);

        Page<Employee> pageContent = employeesRepository.findAll(pageable);
        List<EmployeeDTO> employeesDto = pageContent.getContent().stream()
                .map(EmployeeDTO::new
                ).collect(Collectors.toList());
        return employeesDto;
    }
    /**
     * Gets all the employees based on the user's search query
     * @param query the query the user entered in the search bar
     * @return a lists of EmployeeDTO that somehow match the use query
     * */
    public List<EmployeeDTO> findBySearchQuery(String query,int page,int size){
        Pageable pageable = PageRequest.of(page, size);

        Integer id=null;
        String name =null;
        List <Employee> employees;
        if (query ==null || query.isEmpty()){
            return getAllEmployees(page,size);
        }
        else if (query.matches("\\d+")) {
            id = Integer.valueOf(query);
            employees = employeesRepository.searchByNameOrEmployeeId(id,pageable);
        } else {
            name = query;
            employees = employeesRepository.searchByNameOrEmployeeId(name,pageable);

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
    public List<EmployeeDTO> getFilteredEmployees(String department,int page, int size) {
        List<Employee> employees = new ArrayList<>();
        Pageable pageable = PageRequest.of(page, size);
        if ((department==null||department.isEmpty()))
            return getAllEmployees(page,size);
        else{
            employees= this.employeesRepository.findByDepartment(department,pageable);
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
