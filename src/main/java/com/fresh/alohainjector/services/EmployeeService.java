package com.fresh.alohainjector.services;

import com.fresh.alohainjector.dataAloha.domain.AlohaEmployee;
import com.fresh.alohainjector.dataFresh.domain.FreshEmployee;

import java.util.List;

public interface EmployeeService {

    List<AlohaEmployee> getAllAlohaEmployees();

    List<FreshEmployee> getNewFreshEmployees();

    void saveEmployee(AlohaEmployee alohaEmployee);
}
