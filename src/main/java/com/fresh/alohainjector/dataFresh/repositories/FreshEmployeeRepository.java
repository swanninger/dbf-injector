package com.fresh.alohainjector.dataFresh.repositories;

import com.fresh.alohainjector.dataFresh.domain.FreshEmployee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FreshEmployeeRepository extends JpaRepository<FreshEmployee, String> {
}
