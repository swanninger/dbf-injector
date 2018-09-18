package com.fresh.alohainjector.dataAloha.repositories;

import com.fresh.alohainjector.dataAloha.domain.TerminatedAlohaEmployee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TerminatedEmployeeRepository extends JpaRepository<TerminatedAlohaEmployee, UUID> {
}
