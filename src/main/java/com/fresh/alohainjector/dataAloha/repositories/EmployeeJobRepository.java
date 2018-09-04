package com.fresh.alohainjector.dataAloha.repositories;

import com.fresh.alohainjector.dataAloha.domain.EmployeeJob;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EmployeeJobRepository extends JpaRepository<EmployeeJob, UUID> {
}
