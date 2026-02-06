package org.example.employeemanagement.Repositories;

import org.example.employeemanagement.Entities.Admin;
import org.example.employeemanagement.Entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminsRepository extends JpaRepository<Employee, Integer> {
    Admin findByEmployeeId(int id);
}
