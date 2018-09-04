package com.fresh.alohainjector.bootstrap;

import com.fresh.alohainjector.dataAloha.domain.AlohaEmployee;
import com.fresh.alohainjector.dataAloha.domain.Owner;
import com.fresh.alohainjector.dataFresh.domain.FreshEmployee;
import com.fresh.alohainjector.services.EmployeeService;
import com.fresh.alohainjector.services.OwnerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Slf4j
public class Bootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private final EmployeeService employeeService;
    private final OwnerService ownerService;

    public Bootstrap(EmployeeService employeeService, OwnerService ownerService) {
        this.employeeService = employeeService;
        this.ownerService = ownerService;
    }


    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
//        createEmployee();
        getAlohaEmployees();
//        getFreshEmployees();
//        employeeService.importEmployees();
    }

    public void createEmployee() {
        Owner owner = ownerService.getByOwnerType(0);
        AlohaEmployee emp = new AlohaEmployee();
        emp.setNumber(9997);
        emp.setBOHUser("9997");
        emp.setFirstName("Craig");
        emp.setLastName("Bootspouse");
        emp.setOwner(owner);
        employeeService.saveAlohaEmployee(emp);
    }

    @Transactional
    public void getAlohaEmployees() {
        List<AlohaEmployee> alohaEmployees = employeeService.getAllAlohaEmployees();

        for (AlohaEmployee alohaEmployee : alohaEmployees) {
            System.out.println(alohaEmployee);

        }
    }

    public void getFreshEmployees() {
        List<FreshEmployee> freshEmployees = employeeService.getNewFreshEmployees();

        for (FreshEmployee freshEmployee : freshEmployees) {
            System.out.println(freshEmployee);

        }
    }


}