package org.example.employeemanagement.Repositories;

import org.example.employeemanagement.Entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface EmployeesRepository extends JpaRepository<Employee, Integer> {
    Employee findByEmployeeId(int id);
    List<Employee> findByDateOfBirth(LocalDate date);
    void deleteByEmployeeId(int id);
    @Query("SELECT e FROM Employee e WHERE " +
            ":name IS NOT NULL AND LOWER(e.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Employee>searchByNameOrEmployeeId(String name);

    @Query("SELECT e FROM Employee e WHERE " +
            ":id IS NOT NULL and (e.employeeId) =  :id")
    List<Employee>searchByNameOrEmployeeId(int id);
}
