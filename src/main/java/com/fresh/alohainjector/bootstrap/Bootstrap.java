package com.fresh.alohainjector.bootstrap;

import com.fresh.alohainjector.domain.Employee;
import com.fresh.alohainjector.services.EmployeeService;
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

    public Bootstrap(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }


    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        List<Employee> employees = employeeService.getAllEmployees();

        for (Employee employee : employees) {
            System.out.println(employee);
        }
    }
}