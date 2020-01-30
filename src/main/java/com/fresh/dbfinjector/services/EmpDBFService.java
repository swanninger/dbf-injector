package com.fresh.dbfinjector.services;

import nl.knaw.dans.common.dbflib.Value;

import java.io.IOException;
import java.util.Map;

public interface EmpDBFService {
    Map<String, Value> getEmployeeById(int id);

    Map<String, Value> getEmployeeByName(String firstName, String lastName);

    Map<String, Value> getEmployeeBySsn(int ssn);

    void updateEmployee(Map<String, Value> employeeData);

    void updateEmployees(Map<Integer, Map<String, Value>> employeeData) throws IOException;

    void addEmployee(Map<String, Value> employeeData);

    Boolean terminateEmployee(int id);
}
