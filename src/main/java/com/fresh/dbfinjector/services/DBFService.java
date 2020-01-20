package com.fresh.dbfinjector.services;

import nl.knaw.dans.common.dbflib.Value;

import java.util.Map;

public interface DBFService {
    Map<String, Value> getEmployeeById(int id);

    Map<String, Value> getEmployeeByName(String firstName, String lastName);

    Map<String, Value> getEmployeeBySsn(int ssn);

    void updateEmployee(Map<String, Value> record);

    void addEmployee(Map<String, Value> record);
}
