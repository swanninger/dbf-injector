package com.fresh.dbfinjector.dataAloha.repositories;

import com.fresh.dbfinjector.dataAloha.domain.TerminatedAlohaEmployee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TerminatedEmployeeRepository extends JpaRepository<TerminatedAlohaEmployee, UUID> {
}
