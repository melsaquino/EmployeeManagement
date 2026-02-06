package org.example.employeemanagement.Repositories;

import org.example.employeemanagement.Entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface EmployeesRepository extends JpaRepository<Employee, Integer> {
    Employee findByEmployeeId(int id);
    List<Employee> findByDateOfBirth(LocalDate date);
    void deleteByEmployeeId(int id);

}
