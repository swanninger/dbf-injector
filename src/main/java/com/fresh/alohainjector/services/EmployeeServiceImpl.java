package com.fresh.alohainjector.services;

import com.fresh.alohainjector.configuration.InjectorConfig;
import com.fresh.alohainjector.dataAloha.domain.*;
import com.fresh.alohainjector.dataAloha.repositories.AlohaEmployeeRepository;
import com.fresh.alohainjector.dataAloha.repositories.AlohaJobCodeRepository;
import com.fresh.alohainjector.dataAloha.repositories.PosAccessLevelRepository;
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
    private final OwnerService ownerService;

    private final InjectorConfig injectorConfig;

    public EmployeeServiceImpl(AlohaEmployeeRepository alohaEmployeeRepository, FreshEmployeeRepository freshEmployeeRepository, AlohaJobCodeRepository alohaJobCodeRepository, PosAccessLevelRepository posAccessLevelRepository, OwnerService ownerService, InjectorConfig injectorConfig) {
        this.alohaEmployeeRepository = alohaEmployeeRepository;
        this.freshEmployeeRepository = freshEmployeeRepository;
        this.alohaJobCodeRepository = alohaJobCodeRepository;
        this.posAccessLevelRepository = posAccessLevelRepository;

        this.ownerService = ownerService;
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
    public Iterable<FreshEmployee> getUpdatedFreshEmployees() {
        return freshEmployeeRepository.findAllByDtModifiedAfter(injectorConfig.getLastChecked());
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
        Iterable<FreshEmployee> employees = getUpdatedFreshEmployees();
        Owner owner = ownerService.getByOwnerType(0);

        LocalDateTime lastChecked = injectorConfig.getLastChecked();
        try {
            for (FreshEmployee freshEmployee : employees) {
                if (freshEmployee.getDtModified().isAfter(lastChecked)) {
                    lastChecked = freshEmployee.getDtModified();
                }

                if (freshEmployee.getTermCode() == null) {
                    termEmployee(freshEmployee);
                } else {

                    AlohaEmployee alohaEmployee;
                    Optional<AlohaEmployee> alohaEmployeeOptional = alohaEmployeeRepository.findByBohUser(freshEmployee.getEmpId());

                    if (alohaEmployeeOptional.isPresent()) {
                        alohaEmployee = alohaEmployeeOptional.get();
                    } else {
                        alohaEmployee = new AlohaEmployee();
                        alohaEmployee.setNumber(Integer.parseInt(freshEmployee.getEmpId()));
                        alohaEmployee.setBohUser(freshEmployee.getEmpId());
                        alohaEmployee.setOwner(owner);
                    }

                    alohaEmployee.setFirstName(freshEmployee.getFName());
                    alohaEmployee.setLastName(freshEmployee.getLName());

                    convertJobs(freshEmployee, alohaEmployee);

                    saveAlohaEmployee(alohaEmployee);
                }
            }
            injectorConfig.setLastChecked(lastChecked);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error importing employees");
        }
    }

    /**
     * Convert Jobscodes, PayRates and Access Levels from Fresh to Aloha
     *
     * @param freshEmployee
     * @param alohaEmployee
     */
    private void convertJobs(FreshEmployee freshEmployee, AlohaEmployee alohaEmployee) {
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

    private void termEmployee(FreshEmployee freshEmployee) {

    }
}
