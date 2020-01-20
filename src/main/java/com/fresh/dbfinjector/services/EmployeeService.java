package com.fresh.dbfinjector.services;

import com.fresh.dbfinjector.dataAloha.domain.AlohaEmployee;
import com.fresh.dbfinjector.dataFresh.domain.FreshEmployee;

import java.time.LocalDateTime;

public interface EmployeeService {

    Iterable<AlohaEmployee> getAllAlohaEmployees();

    Iterable<FreshEmployee> getNewFreshEmployees();

    Iterable<FreshEmployee> getUpdatedFreshEmployees(LocalDateTime lastChecked);


    AlohaEmployee saveAlohaEmployee(AlohaEmployee alohaEmployee);

    FreshEmployee saveFreshEmployee(FreshEmployee freshEmployee);

    void importEmployees();

//    void mapAccess();
}
