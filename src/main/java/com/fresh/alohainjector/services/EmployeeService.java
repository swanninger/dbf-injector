package com.fresh.alohainjector.services;

import com.fresh.alohainjector.dataAloha.domain.AlohaEmployee;
import com.fresh.alohainjector.dataFresh.domain.FreshEmployee;

import java.util.List;

public interface EmployeeService {

    Iterable<AlohaEmployee> getAllAlohaEmployees();

    Iterable<FreshEmployee> getNewFreshEmployees();

    Iterable<FreshEmployee> getUpdatedFreshEmployees();

    AlohaEmployee saveAlohaEmployee(AlohaEmployee alohaEmployee);

    FreshEmployee saveFreshEmployee(FreshEmployee freshEmployee);

    void importEmployees();

//    void mapAccess();
}
