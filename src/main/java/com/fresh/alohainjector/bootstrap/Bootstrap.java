package com.fresh.alohainjector.bootstrap;

import com.fresh.alohainjector.domain.Employee;
import com.fresh.alohainjector.domain.Owner;
import com.fresh.alohainjector.services.EmployeeService;
import com.fresh.alohainjector.services.OwnerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

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
        createEmployee();
//        getEmployees();
    }

    public void createEmployee() {
        Owner owner = ownerService.getByOwnerType(0);
        Employee emp = new Employee();
        emp.setNumber(9997);
        emp.setBOHUser("9997");
        emp.setFirstName("Craig");
        emp.setLastName("Bootspouse");
        emp.setOwner(owner);
        employeeService.saveEmployee(emp);
    }

    public void getEmployees() {
        List<Employee> employees = employeeService.getAllEmployees();

        for (Employee employee : employees) {
            System.out.println(employee);

        }
    }
}