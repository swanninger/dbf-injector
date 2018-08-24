package com.fresh.alohainjector.repositories;

import com.fresh.alohainjector.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, String> {
}
