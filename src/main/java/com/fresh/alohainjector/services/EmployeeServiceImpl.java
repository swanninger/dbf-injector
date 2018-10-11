package com.fresh.alohainjector.services;

import com.fresh.alohainjector.configuration.InjectorConfig;
import com.fresh.alohainjector.dataAloha.domain.*;
import com.fresh.alohainjector.dataAloha.repositories.*;
import com.fresh.alohainjector.dataFresh.domain.FreshEmployee;
import com.fresh.alohainjector.dataFresh.repositories.FreshEmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {
    private final AlohaEmployeeRepository alohaEmployeeRepository;
    private final FreshEmployeeRepository freshEmployeeRepository;
    private final AlohaJobCodeRepository alohaJobCodeRepository;
    private final PosAccessLevelRepository posAccessLevelRepository;
    private final SecurityRoleRepository securityRoleRepository;
    private final TerminatedEmployeeRepository terminatedEmployeeRepository;
    private final OwnerService ownerService;
    private final MessageService messageService;

    private final InjectorConfig injectorConfig;

    public EmployeeServiceImpl(AlohaEmployeeRepository alohaEmployeeRepository, FreshEmployeeRepository freshEmployeeRepository, AlohaJobCodeRepository alohaJobCodeRepository, PosAccessLevelRepository posAccessLevelRepository, SecurityRoleRepository securityRoleRepository, TerminatedEmployeeRepository terminatedEmployeeRepository, OwnerService ownerService, MessageService messageService, InjectorConfig injectorConfig) {
        this.alohaEmployeeRepository = alohaEmployeeRepository;
        this.freshEmployeeRepository = freshEmployeeRepository;
        this.alohaJobCodeRepository = alohaJobCodeRepository;
        this.posAccessLevelRepository = posAccessLevelRepository;
        this.securityRoleRepository = securityRoleRepository;
        this.terminatedEmployeeRepository = terminatedEmployeeRepository;

        this.ownerService = ownerService;
        this.messageService = messageService;
        this.injectorConfig = injectorConfig;
    }


    @Override
    public Iterable<AlohaEmployee> getAllAlohaEmployees() {
        return alohaEmployeeRepository.findAll();
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
    public AlohaEmployee saveAlohaEmployee(AlohaEmployee alohaEmployee) {
        return alohaEmployeeRepository.save(alohaEmployee);
    }

    @Override
    public FreshEmployee saveFreshEmployee(FreshEmployee freshEmployee) {
        return freshEmployeeRepository.save(freshEmployee);
    }

    @Override
    public void importEmployees() {
        log.warn("Polling Emp Maintenance for updated records.");
        // Get the date of last import from properties
        LocalDateTime lastChecked = injectorConfig.getLastChecked();

        StringBuilder msg = new StringBuilder();

        // If first run set time to Epoch
        if (lastChecked == null) {
            lastChecked = LocalDateTime.MIN;
        }

        Iterable<FreshEmployee> employees = getUpdatedFreshEmployees(lastChecked);

        // grab owner FKey
        Owner owner = ownerService.getByOwnerType(0);

        try {
            for (FreshEmployee freshEmployee : employees) {
                // See if this is the newest record and update last checked accordingly
                if (freshEmployee.getDtModified().isAfter(lastChecked)) {
                    lastChecked = freshEmployee.getDtModified();
                }

                // If employee has been terminated
                if (freshEmployee.getTermCode() != null) {
                    terminateEmployee(freshEmployee);
                    msg.append(freshEmployee.getEmpId()).append(" terminated.\n");
                }
                else {
                    AlohaEmployee alohaEmployee;

                    //Check if employee is present in Aloha
                    Optional<AlohaEmployee> alohaEmployeeOptional = alohaEmployeeRepository.findByBohUser(freshEmployee.getEmpId());
                    if (alohaEmployeeOptional.isPresent()) {
                        alohaEmployee = alohaEmployeeOptional.get();
                    }
                    else { // create new employee
                        alohaEmployee = new AlohaEmployee();
                        alohaEmployee.setNumber(Integer.parseInt(freshEmployee.getEmpId()));
                        alohaEmployee.setBohUser(freshEmployee.getEmpId());
                        alohaEmployee.setOwner(owner);
                    }
                    // Set BOH Security
                    setBohSecurity(freshEmployee,alohaEmployee);

                    alohaEmployee.setFirstName(freshEmployee.getFName());
                    alohaEmployee.setLastName(freshEmployee.getLName());

                    convertJobs(freshEmployee, alohaEmployee);

                    saveAlohaEmployee(alohaEmployee);
                    log.warn("Employee " + alohaEmployee.getBohUser() + " saved.");
                    msg.append(freshEmployee.getEmpId()).append(" added.\n");
                }
            }
            injectorConfig.setLastChecked(lastChecked);

        } catch (Exception e) {
            e.printStackTrace();
            messageService.sendCmdMsg("Error importing employees");
            log.error("Error importing employees");
        }
        messageService.sendCmdMsg("Employees Updated\n" + msg);
        log.warn("Employees Updated");
    }

    /**
     * Convert JobCodes, PayRates and Access Levels from Fresh to Aloha
     *
     * @param freshEmployee
     * @param alohaEmployee
     */
    private void convertJobs(FreshEmployee freshEmployee, AlohaEmployee alohaEmployee) {
        //Delete job entries from aloha to prevent duplication
        alohaEmployee.getEmployeeJobs().clear();

        // start arrays
        Short jobCodes[] = {freshEmployee.getJobCode1(), freshEmployee.getJobCode2(), freshEmployee.getJobCode3(),
                freshEmployee.getJobCode4(), freshEmployee.getJobCode5(), freshEmployee.getJobCode6(), freshEmployee.getJobCode7(),
                freshEmployee.getJobCode8(), freshEmployee.getJobCode9(), freshEmployee.getJobCode10()};

        Double payRates[] = {freshEmployee.getPayRate1(), freshEmployee.getPayRate2(), freshEmployee.getPayRate3(), freshEmployee.getPayRate4(),
                freshEmployee.getPayRate5(), freshEmployee.getPayRate6(), freshEmployee.getPayRate7(), freshEmployee.getPayRate8(),
                freshEmployee.getPayRate9(), freshEmployee.getPayRate10()};

        Short acclvls[] = {freshEmployee.getAccLvl1(), freshEmployee.getAccLvl2(), freshEmployee.getAccLvl3(), freshEmployee.getAccLvl4(),
                freshEmployee.getAccLvl5(), freshEmployee.getAccLvl6(), freshEmployee.getAccLvl7(), freshEmployee.getAccLvl8(),
                freshEmployee.getAccLvl9(), freshEmployee.getAccLvl10()};
        // end arrays

        // start array mapping
        for (int i = 0; i < 10; i++) {
            if (jobCodes[i] != null) {
                Optional<AlohaJobCode> alohaJobCode = alohaJobCodeRepository.findByNumber((int) jobCodes[i]);
                if (alohaJobCode.isPresent()) {
                    EmployeeJob employeeJob = new EmployeeJob();
                    employeeJob.setAlohaJobCode(alohaJobCode.get());
                    alohaEmployee.addEmployeeJob(employeeJob);

                    if (acclvls[i] != null && acclvls[i] > 0) {
                        Optional<PosAccessLevel> posAccessLevel = posAccessLevelRepository.findByNumber((int) acclvls[i]);
                        posAccessLevel.ifPresent(employeeJob::setPosAccessLevel);
                    }

                    if (payRates[i] != null) {
                        PayRateChange payRateChange = new PayRateChange();
                        payRateChange.setPayRate(payRates[i]);
                        payRateChange.setEffectiveDate(LocalDateTime.now());
                        payRateChange.setModifiedDate(LocalDateTime.now());
                        employeeJob.addPayRateChange(payRateChange);
                    }
                }
            }
        }
        // end array mapping
    }

    private void setBohSecurity(FreshEmployee freshEmployee, AlohaEmployee alohaEmployee) {
        // Set BOH Security
        if (freshEmployee.getBohSecurity() == 0){
            alohaEmployee.setSystemAccess(0);
            alohaEmployee.setSecurityRole(null);
        }else {
            alohaEmployee.setSystemAccess(2);
            Optional<SecurityRole> role = securityRoleRepository.findByNumber((int) freshEmployee.getBohSecurity());
            role.ifPresent(alohaEmployee::setSecurityRole);
        }
    }

    private void terminateEmployee(FreshEmployee freshEmployee) {
        //Check if employee is present in Aloha
        Optional<AlohaEmployee> alohaEmployeeOptional = alohaEmployeeRepository.findByBohUser(freshEmployee.getEmpId());
        if (alohaEmployeeOptional.isPresent()) {
            // Convert to terminated employee before deleting
            AlohaEmployee alohaEmployee = alohaEmployeeOptional.get();
            TerminatedAlohaEmployee termEmployee = new TerminatedAlohaEmployee();

            termEmployee.setFirstName(alohaEmployee.getFirstName());
            termEmployee.setLastName(alohaEmployee.getLastName());
            termEmployee.setOwner(alohaEmployee.getOwner());
            termEmployee.setSystemAccess(alohaEmployee.getSystemAccess());
            termEmployee.setSecurityRole(alohaEmployee.getSecurityRole());
            termEmployee.setNumber(alohaEmployee.getNumber());
            termEmployee.setBohUser(alohaEmployee.getBohUser());

            for (EmployeeJob employeeJob : alohaEmployee.getEmployeeJobs()) {
                TerminatedEmployeeJob termJob = new TerminatedEmployeeJob();

                termJob.setAlohaJobCode(employeeJob.getAlohaJobCode());
                termJob.setPosAccessLevel(employeeJob.getPosAccessLevel());
                termEmployee.addEmployeeJob(termJob);

                for (PayRateChange payRateChange : employeeJob.getPayRateChanges()) {
                    TerminatedPayRateChange termPayRateChange = new TerminatedPayRateChange();

                    termPayRateChange.setPayRate(payRateChange.getPayRate());
                    termPayRateChange.setEffectiveDate(payRateChange.getEffectiveDate());
                    termPayRateChange.setModifiedDate(payRateChange.getModifiedDate());
                    termJob.addPayRateChange(termPayRateChange);
                }

            }
            terminatedEmployeeRepository.save(termEmployee);
            alohaEmployeeRepository.delete(alohaEmployee);

            log.info(freshEmployee.getEmpId() + " removed.");
        }
        else{
            log.info("Employee " + freshEmployee.getEmpId() + " not present.");
        }
    }
}
