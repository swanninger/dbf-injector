package com.fresh.alohainjector.dataAloha.repositories;

import com.fresh.alohainjector.dataAloha.domain.AlohaEmployee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AlohaEmployeeRepository extends JpaRepository<AlohaEmployee, UUID> {
    List<AlohaEmployee> findAllByBohUser(Iterable<String> empIds);

    Optional<AlohaEmployee> findByBohUser(String empId);

    Optional<AlohaEmployee> findByNumber(Integer employeeNumber);

    void deleteByBohUser(String empId);
}
