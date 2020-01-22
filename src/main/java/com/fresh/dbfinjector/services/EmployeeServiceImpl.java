package com.fresh.dbfinjector.services;

import com.fresh.dbfinjector.configuration.InjectorConfig;
import com.fresh.dbfinjector.dataFresh.domain.FreshEmployee;
import com.fresh.dbfinjector.dataFresh.repositories.FreshEmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import nl.knaw.dans.common.dbflib.NumberValue;
import nl.knaw.dans.common.dbflib.StringValue;
import nl.knaw.dans.common.dbflib.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {
    private final FreshEmployeeRepository freshEmployeeRepository;
    private final InjectorConfig injectorConfig;
    private final EmpDBFService empDBFService;

    public EmployeeServiceImpl(FreshEmployeeRepository freshEmployeeRepository, InjectorConfig injectorConfig, EmpDBFService empDBFService) {
        this.freshEmployeeRepository = freshEmployeeRepository;
        this.injectorConfig = injectorConfig;
        this.empDBFService = empDBFService;
    }

    @Override
    public Iterable<FreshEmployee> getNewFreshEmployees() {
        return freshEmployeeRepository.findAllByImgNameIgnoreCase("NEW");
    }

    @Override
    public Iterable<FreshEmployee> getUpdatedFreshEmployees(LocalDateTime lastChecked) {
        return freshEmployeeRepository.findAllByDtModifiedAfterAndImgNameIgnoreCase(lastChecked, "COMPLETED");
    }

    @Override
    public FreshEmployee saveFreshEmployee(FreshEmployee freshEmployee) {
        return freshEmployeeRepository.save(freshEmployee);
    }

    @Override
    public void importEmployees() {
        log.warn("Polling Emp Maintenance for updated records.");
//        // Get the date of last import from properties
        LocalDateTime lastChecked = injectorConfig.getLastChecked();

//        // If first run set time to Epoch
        if (lastChecked == null) {
            lastChecked = LocalDateTime.MIN;
        }

        Iterable<FreshEmployee> employees = getUpdatedFreshEmployees(lastChecked);

        try {
            for (FreshEmployee freshEmployee : employees) {
                // See if this is the newest record and update last checked accordingly
                if (freshEmployee.getDtModified().isAfter(lastChecked)) {
                    lastChecked = freshEmployee.getDtModified();
                }

                // If employee has been terminated
                if (freshEmployee.getTermCode() != null) {
                    try {
                        terminateEmployee(freshEmployee);
                        log.warn(freshEmployee.getEmpId() + " terminated.");
                    } catch (Exception e) {
                        log.error("Error deleting employee " + freshEmployee.getEmpId() + e.toString());
                    }
                } else {
                    Map<String, Value> empData = convertToValueMap(freshEmployee);
                    empDBFService.updateEmployee(empData);

                    log.warn("Employee " + freshEmployee.getEmpId() + " saved.");
                }
            }
            injectorConfig.setLastChecked(lastChecked);

        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error importing employees");
        }
        log.warn("Employees Updated");
    }

    private Map<String, Value> convertToValueMap(FreshEmployee freshEmployee){
        Map<String, Value> empData = new HashMap<>();
        empData.put("ID", new StringValue(freshEmployee.getEmpId()));
        empData.put("USERNUMBER", new StringValue(freshEmployee.getEmpId()));
        empData.put("SSN", new StringValue("000000000"));
        empData.put("SSNTEXT", new StringValue("000000000"));
        empData.put("FIRSTNAME", new StringValue(freshEmployee.getFName()));
        empData.put("LASTNAME", new StringValue(freshEmployee.getLName()));
        empData.put("NICKNAME", new StringValue(freshEmployee.getFName()));


        empData.put("", new StringValue(freshEmployee.getEmpId()));

        return empData;
    }

    private void terminateEmployee(FreshEmployee freshEmployee) {

    }
}
