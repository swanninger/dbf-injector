package com.fresh.dbfinjector.dataAloha.repositories;

import com.fresh.dbfinjector.dataAloha.domain.EmployeeJob;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EmployeeJobRepository extends JpaRepository<EmployeeJob, UUID> {
}
