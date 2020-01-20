package com.fresh.dbfinjector.bootstrap;

import com.fresh.dbfinjector.dataAloha.domain.AlohaEmployee;
import com.fresh.dbfinjector.dataAloha.domain.Owner;
import com.fresh.dbfinjector.dataFresh.domain.FreshEmployee;
import com.fresh.dbfinjector.services.EmployeeService;
import com.fresh.dbfinjector.services.OwnerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.time.LocalDateTime;
import java.util.Properties;

@Component
@Slf4j
public class Initializer implements ApplicationListener<ContextRefreshedEvent> {

    private final EmployeeService employeeService;
    private final OwnerService ownerService;

    public Initializer(EmployeeService employeeService, OwnerService ownerService) {
        this.employeeService = employeeService;
        this.ownerService = ownerService;
    }


    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        log.warn("Injector Start.");
//        employeeService.importEmployees();
        log.warn("Injection complete.");
    }

    //Test classes
    public void createEmployee() {
        Owner owner = ownerService.getByOwnerType(0);
        AlohaEmployee emp = new AlohaEmployee();
        emp.setNumber(9997);
        emp.setBohUser("9997");
        emp.setFirstName("Craig");
        emp.setLastName("Bootspouse");
        emp.setOwner(owner);
        employeeService.saveAlohaEmployee(emp);
    }

    public void getAlohaEmployees() {
        Iterable<AlohaEmployee> alohaEmployees = employeeService.getAllAlohaEmployees();

        for (AlohaEmployee alohaEmployee : alohaEmployees) {
            System.out.println(alohaEmployee);

        }
    }

    public void getFreshEmployees() {
        Iterable<FreshEmployee> freshEmployees = employeeService.getNewFreshEmployees();

        for (FreshEmployee freshEmployee : freshEmployees) {
            System.out.println(freshEmployee);

        }
    }

    public void testProperties() {
        Properties prop = new Properties();
        InputStream input = null;
        OutputStream output = null;

        try {

            input = new FileInputStream("application.properties");

            // load a properties file
            prop.load(input);
            log.info("props loaded");

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        try {
            output = new FileOutputStream("application.properties");

            prop.setProperty("injector.lastChecked", LocalDateTime.now().toString());
            log.info("date set");

            prop.store(output,null);

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}