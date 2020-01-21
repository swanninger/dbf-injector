package com.fresh.dbfinjector.services;

import nl.knaw.dans.common.dbflib.Value;

import java.util.Map;

public class EmpDBFServiceImpl implements EmpDBFService {

    @Override
    public Map<String, Value> getEmployeeById(int id) {
        return null;
    }

    @Override
    public Map<String, Value> getEmployeeByName(String firstName, String lastName) {
        return null;
    }

    @Override
    public Map<String, Value> getEmployeeBySsn(int ssn) {
        return null;
    }

    @Override
    public void updateEmployee(Map<String, Value> record) {

    }

    @Override
    public void addEmployee(Map<String, Value> record) {

    }

    @Override
    public Boolean terminateEmployee(int id) {
        return null;
    }
}
