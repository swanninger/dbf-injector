package com.fresh.alohainjector.dataAloha.repositories;

import com.fresh.alohainjector.dataAloha.domain.AlohaEmployee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlohaEmployeeRepository extends JpaRepository<AlohaEmployee, String> {
}
