package com.fresh.dbfinjector.services;

import com.fresh.dbfinjector.configuration.InjectorConfig;
import lombok.extern.slf4j.Slf4j;
import nl.knaw.dans.common.dbflib.*;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class EmpDBFServiceImpl implements EmpDBFService {
    private final InjectorConfig injectorConfig;

    public EmpDBFServiceImpl(InjectorConfig injectorConfig) {
        this.injectorConfig = injectorConfig;
    }

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
    public void updateEmployee(Map<String, Value> employeeData) {
        final Table table = new Table(new File(injectorConfig.getNewDataPath() + "emp.dbf"));
        Record newRecord = new Record(employeeData);
        boolean exists = false; //track if employee has been found in DBF

        try {
            table.open();
            Record record;
            for (int i = 0; i < table.getRecordCount(); i++) {
                record = table.getRecordAt(i);
                if (record.isMarkedDeleted()) continue; // null pointer if you try and read from a deleted record

                if (record.getNumberValue("ID").intValue() == newRecord.getNumberValue("ID").intValue()) { // is this employee?
                    table.updateRecordAt(i, newRecord, false);
                    exists = true;
                    break;
                }
            }
            if (!exists){ //if employee is not in DBF then append
                table.addRecord(newRecord);
            }
        } catch (IOException | DbfLibException e) {
            e.printStackTrace();
        } finally {
            try {
                table.close();
            } catch (IOException ex) {
                log.error("Unable to close the table");
            }
        }
    }

    @Override
    public void addEmployee(Map<String, Value> employeeData) {
        final Table table = new Table(new File(injectorConfig.getNewDataPath() + "emp.dbf"));
        Record newRecord = new Record(employeeData);

        try {
            table.open();
            table.addRecord(newRecord);
        } catch (IOException | DbfLibException e) {
            e.printStackTrace();
        }finally {
            try {
                table.close();
            } catch (IOException ex) {
                log.error("Unable to close the table");
            }
        }
    }

    @Override
    public Boolean terminateEmployee(int id) {
        return null;
    }
}
