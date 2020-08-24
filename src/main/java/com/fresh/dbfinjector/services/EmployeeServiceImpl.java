package com.fresh.dbfinjector.services;

import com.fresh.dbfinjector.configuration.InjectorConfig;
import com.fresh.dbfinjector.dataFresh.domain.FreshEmployee;
import com.fresh.dbfinjector.dataFresh.repositories.FreshEmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import nl.knaw.dans.common.dbflib.NumberValue;
import nl.knaw.dans.common.dbflib.StringValue;
import nl.knaw.dans.common.dbflib.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
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
            Map<Integer, Map<String, Value>> employeeMap = new HashMap<>();

            for (FreshEmployee freshEmployee : employees) {
                if (injectorConfig.getEmpIdsToSkip().contains(freshEmployee.getEmpId())) continue; // check skip list

                if (!injectorConfig.isImportNines()) {
                    if (freshEmployee.getEmpId().contentEquals("9999")) continue; //skip tech acct
                }

                // See if this is the newest record and update last checked accordingly
                if (freshEmployee.getDtModified().isAfter(lastChecked)) {
                    lastChecked = freshEmployee.getDtModified();
                }

                Map<String, Value> empData = convertToValueMap(freshEmployee);
                employeeMap.put(Integer.parseInt(freshEmployee.getEmpId()), empData);

//                log.warn("Employee " + freshEmployee.getEmpId() + " added to map.");

            }
            empDBFService.updateEmployees(employeeMap);
            injectorConfig.setLastChecked(lastChecked);
            log.warn("Employees Updated");

        } catch (IOException e) {
            e.printStackTrace();
            log.error("Error importing employees, file not found");
        } catch (NumberFormatException e) {
            e.printStackTrace();
            log.error("Error importing employees");
        }
    }

    private Map<String, Value> convertToValueMap(FreshEmployee freshEmployee) {
        Map<String, Value> empData = new HashMap<>();
        empData.put("ID", new NumberValue(Integer.parseInt(freshEmployee.getEmpId() == null ? "0" : freshEmployee.getEmpId())));
        empData.put("USERNUMBER", new NumberValue(Integer.parseInt(freshEmployee.getEmpId() == null ? "0" : freshEmployee.getEmpId())));
        if (!injectorConfig.isSkipSsn()){
            empData.put("SSN", new NumberValue(000000000));
            empData.put("SSNTEXT", new NumberValue(000000000));
        }

        empData.put("FIRSTNAME", new StringValue(freshEmployee.getFName()));
        empData.put("LASTNAME", new StringValue(freshEmployee.getLName()));
        empData.put("NICKNAME", new StringValue(freshEmployee.getFName()));
        empData.put("SECLEVEL", new NumberValue(freshEmployee.getBohSecurity() == null ? 0 : freshEmployee.getBohSecurity()));
//
        empData.put("JOBCODE1", new NumberValue(freshEmployee.getJobCode1() == null ? 0 : freshEmployee.getJobCode1()));
        empData.put("JOBCODE2", new NumberValue(freshEmployee.getJobCode2() == null ? 0 : freshEmployee.getJobCode2()));
        empData.put("JOBCODE3", new NumberValue(freshEmployee.getJobCode3() == null ? 0 : freshEmployee.getJobCode3()));
        empData.put("JOBCODE4", new NumberValue(freshEmployee.getJobCode4() == null ? 0 : freshEmployee.getJobCode4()));
        empData.put("JOBCODE5", new NumberValue(freshEmployee.getJobCode5() == null ? 0 : freshEmployee.getJobCode5()));
        empData.put("JOBCODE6", new NumberValue(freshEmployee.getJobCode6() == null ? 0 : freshEmployee.getJobCode6()));
        empData.put("JOBCODE7", new NumberValue(freshEmployee.getJobCode7() == null ? 0 : freshEmployee.getJobCode7()));
        empData.put("JOBCODE8", new NumberValue(freshEmployee.getJobCode8() == null ? 0 : freshEmployee.getJobCode8()));
        empData.put("JOBCODE9", new NumberValue(freshEmployee.getJobCode9() == null ? 0 : freshEmployee.getJobCode9()));
        empData.put("JOBCODE10", new NumberValue(freshEmployee.getJobCode10() == null ? 0 : freshEmployee.getJobCode10()));

        empData.put("PAYRATE1", new NumberValue(freshEmployee.getPayRate1() == null ? 0 : freshEmployee.getPayRate1()));
        empData.put("PAYRATE2", new NumberValue(freshEmployee.getPayRate2() == null ? 0 : freshEmployee.getPayRate2()));
        empData.put("PAYRATE3", new NumberValue(freshEmployee.getPayRate3() == null ? 0 : freshEmployee.getPayRate3()));
        empData.put("PAYRATE4", new NumberValue(freshEmployee.getPayRate4() == null ? 0 : freshEmployee.getPayRate4()));
        empData.put("PAYRATE5", new NumberValue(freshEmployee.getPayRate5() == null ? 0 : freshEmployee.getPayRate5()));
        empData.put("PAYRATE6", new NumberValue(freshEmployee.getPayRate6() == null ? 0 : freshEmployee.getPayRate6()));
        empData.put("PAYRATE7", new NumberValue(freshEmployee.getPayRate7() == null ? 0 : freshEmployee.getPayRate7()));
        empData.put("PAYRATE8", new NumberValue(freshEmployee.getPayRate8() == null ? 0 : freshEmployee.getPayRate8()));
        empData.put("PAYRATE9", new NumberValue(freshEmployee.getPayRate9() == null ? 0 : freshEmployee.getPayRate9()));
        empData.put("PAYRATE10", new NumberValue(freshEmployee.getPayRate10() == null ? 0 : freshEmployee.getPayRate10()));

        empData.put("ACCESS1", new NumberValue(freshEmployee.getAccLvl1() == null ? 0 : freshEmployee.getAccLvl1()));
        empData.put("ACCESS2", new NumberValue(freshEmployee.getAccLvl2() == null ? 0 : freshEmployee.getAccLvl2()));
        empData.put("ACCESS3", new NumberValue(freshEmployee.getAccLvl3() == null ? 0 : freshEmployee.getAccLvl3()));
        empData.put("ACCESS4", new NumberValue(freshEmployee.getAccLvl4() == null ? 0 : freshEmployee.getAccLvl4()));
        empData.put("ACCESS5", new NumberValue(freshEmployee.getAccLvl5() == null ? 0 : freshEmployee.getAccLvl5()));
        empData.put("ACCESS6", new NumberValue(freshEmployee.getAccLvl6() == null ? 0 : freshEmployee.getAccLvl6()));
        empData.put("ACCESS7", new NumberValue(freshEmployee.getAccLvl7() == null ? 0 : freshEmployee.getAccLvl7()));
        empData.put("ACCESS8", new NumberValue(freshEmployee.getAccLvl8() == null ? 0 : freshEmployee.getAccLvl8()));
        empData.put("ACCESS9", new NumberValue(freshEmployee.getAccLvl9() == null ? 0 : freshEmployee.getAccLvl9()));
        empData.put("ACCESS10", new NumberValue(freshEmployee.getAccLvl10() == null ? 0 : freshEmployee.getAccLvl10()));

        if (freshEmployee.getTermCode() == null) {
            empData.put("ZAPID", new NumberValue(0));
            empData.put("TERMINATED", new StringValue("N"));
        } else {
            empData.put("ZAPID", new NumberValue(freshEmployee.getTermCode()));
            empData.put("TERMINATED", new StringValue("Y"));
        }

        return empData;
    }
}
