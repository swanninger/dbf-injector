package com.fresh.dbfinjector.services;

import com.fresh.dbfinjector.configuration.InjectorConfig;
import nl.knaw.dans.common.dbflib.NumberValue;
import nl.knaw.dans.common.dbflib.Value;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class EmpDBFServiceImplTest {
    EmpDBFService empDBFService;

    @Mock
    InjectorConfig injectorConfig;
    @Mock
    ApplicationPropertyService applicationPropertyService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        applicationPropertyService = new ApplicationPropertyServiceImpl();
        injectorConfig = new InjectorConfig(applicationPropertyService);
        empDBFService = new EmpDBFServiceImpl(injectorConfig);
    }

    @Test
    void getEmployeeById() {
        empDBFService.getEmployeeById(108);
    }

    @Test
    void getEmployeeByName() {
    }

    @Test
    void getEmployeeBySsn() {
    }

    @Test
    void updateEmployee() {
        Map<String, Value> employeeData = new HashMap<>();
        employeeData.put("ID", new NumberValue(108));
        employeeData.put("OWNERID", new NumberValue(1));

        empDBFService.updateEmployee(employeeData);
    }

    @Test
    void addEmployee() {
    }

    @Test
    void terminateEmployee() {
    }
}