package com.fresh.alohainjector.dataFresh.repositories;

import com.fresh.alohainjector.dataFresh.domain.FreshEmployee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface FreshEmployeeRepository extends JpaRepository<FreshEmployee, String> {
    List<FreshEmployee> findAllByImgNameIgnoreCase (String img_name);

    List<FreshEmployee> findAllByDtModifiedAfter(LocalDateTime lastChecked);
}
