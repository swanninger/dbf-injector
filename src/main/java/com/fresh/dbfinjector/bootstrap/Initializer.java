package com.fresh.dbfinjector.bootstrap;

import com.fresh.dbfinjector.configuration.InjectorConfig;
import com.fresh.dbfinjector.dataFresh.domain.FreshEmployee;
import com.fresh.dbfinjector.services.EmpDBFService;
import com.fresh.dbfinjector.services.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import nl.knaw.dans.common.dbflib.NumberValue;
import nl.knaw.dans.common.dbflib.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class Initializer implements ApplicationListener<ContextRefreshedEvent> {

    private final EmployeeService employeeService;
    private final InjectorConfig injectorConfig;
    private final EmpDBFService empDBFService;

    public Initializer(EmployeeService employeeService, InjectorConfig injectorConfig, EmpDBFService empDBFService) {
        this.employeeService = employeeService;
        this.injectorConfig = injectorConfig;
        this.empDBFService = empDBFService;
    }


    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        log.warn("Injector Start.");
//        employeeService.importEmployees();
//        testProperties();
//        updateEmployeeTest();
        log.warn("Injection complete.");
    }

    //Test classes
    public void createEmployee() {

    }

    public void getFreshEmployees() {
        Iterable<FreshEmployee> freshEmployees = employeeService.getNewFreshEmployees();

        for (FreshEmployee freshEmployee : freshEmployees) {
            System.out.println(freshEmployee);

        }
    }

    public void testProperties() {
        log.warn(injectorConfig.getNewDataPath());
    }

    public void updateEmployeeTest() {
        Map<String, Value> employeeData = new HashMap<>();
        employeeData.put("ID", new NumberValue(108));
        employeeData.put("OWNERID", new NumberValue(1));

        empDBFService.updateEmployee(employeeData);
    }


}