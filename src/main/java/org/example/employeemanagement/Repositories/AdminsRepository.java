package org.example.employeemanagement.Repositories;

import org.example.employeemanagement.Entities.Admin;
import org.example.employeemanagement.Entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
/** JPA repository that interacts with the admin table in the database
 * */
public interface AdminsRepository extends JpaRepository<Employee, Integer> {
    Admin findByEmployeeId(int id);
}
