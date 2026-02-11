package org.example.employeemanagement.Repositories;

import org.example.employeemanagement.Entities.HR;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HRsRepository extends JpaRepository<HR, Integer> {

}

