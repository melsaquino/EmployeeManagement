package org.example.employeemanagement.Repositories;

import org.example.employeemanagement.Entities.Accounting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountingRepository extends JpaRepository<Accounting, Integer> {
}
