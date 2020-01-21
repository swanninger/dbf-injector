package com.fresh.dbfinjector.services;

import com.fresh.dbfinjector.dataFresh.domain.FreshEmployee;

import java.time.LocalDateTime;

public interface EmployeeService {


    Iterable<FreshEmployee> getNewFreshEmployees();

    Iterable<FreshEmployee> getUpdatedFreshEmployees(LocalDateTime lastChecked);

    FreshEmployee saveFreshEmployee(FreshEmployee freshEmployee);

    void importEmployees();

//    void mapAccess();
}
