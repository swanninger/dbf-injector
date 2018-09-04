package com.fresh.alohainjector.services;

import com.fresh.alohainjector.dataAloha.domain.AlohaEmployee;
import com.fresh.alohainjector.dataAloha.domain.AlohaJobCode;
import com.fresh.alohainjector.dataAloha.domain.EmployeeJob;
import com.fresh.alohainjector.dataAloha.domain.Owner;
import com.fresh.alohainjector.dataAloha.repositories.AlohaEmployeeRepository;
import com.fresh.alohainjector.dataAloha.repositories.AlohaJobCodeRepository;
import com.fresh.alohainjector.dataFresh.domain.FreshEmployee;
import com.fresh.alohainjector.dataFresh.repositories.FreshEmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {
    private final AlohaEmployeeRepository alohaEmployeeRepository;
    private final FreshEmployeeRepository freshEmployeeRepository;
    private final AlohaJobCodeRepository alohaJobCodeRepository;
    private final OwnerService ownerService;

    public EmployeeServiceImpl(AlohaEmployeeRepository alohaEmployeeRepository, FreshEmployeeRepository freshEmployeeRepository, AlohaJobCodeRepository alohaJobCodeRepository, OwnerService ownerService) {
        this.alohaEmployeeRepository = alohaEmployeeRepository;
        this.freshEmployeeRepository = freshEmployeeRepository;
        this.alohaJobCodeRepository = alohaJobCodeRepository;

        this.ownerService = ownerService;
    }


    @Override
    public List<AlohaEmployee> getAllAlohaEmployees() {
        return alohaEmployeeRepository.findAll();
    }

    @Override
    public List<FreshEmployee> getNewFreshEmployees() {
        return freshEmployeeRepository.findAllByImgNameIgnoreCase("NEW");
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
        List<FreshEmployee> employees = getNewFreshEmployees();
        Owner owner = ownerService.getByOwnerType(0);

        for (FreshEmployee freshEmployee : employees) {
            try {
                AlohaEmployee alohaEmployee = convertFreshToAloha(freshEmployee, owner);
                saveAlohaEmployee(alohaEmployee);

                freshEmployee.setImgName("COMPLETED");
                saveFreshEmployee(freshEmployee);
                log.info("Employee: " + freshEmployee.getEmpId() + " saved.");
            } catch (Exception e) {
                log.error("Employee: " + freshEmployee.getEmpId() + " could not be saved.");
            }
        }
    }

    private AlohaEmployee convertFreshToAloha(FreshEmployee freshEmployee, Owner owner) {
        AlohaEmployee alohaEmployee = new AlohaEmployee();

        alohaEmployee.setNumber(Integer.parseInt(freshEmployee.getEmpId()));
        alohaEmployee.setBOHUser(freshEmployee.getEmpId());
        alohaEmployee.setFirstName(freshEmployee.getFName());
        alohaEmployee.setLastName(freshEmployee.getLName());
        alohaEmployee.setOwner(owner);

        alohaEmployee = alohaEmployeeRepository.save(alohaEmployee);

        List<EmployeeJob> employeeJobs = new LinkedList<>();

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

        for (int i = 0; i < 10; i++) {
            if (jobCodes[i] != null) {
                Optional<AlohaJobCode> alohaJobCode = alohaJobCodeRepository.findByNumber(jobCodes[i]);
                if (alohaJobCode.isPresent()) {
                    EmployeeJob employeeJob = new EmployeeJob();
                    employeeJob.setAlohaJobCode(alohaJobCode.get());
                    employeeJobs.add(new EmployeeJob());
                }
            }
        }


        alohaEmployee.setEmployeeJobs(employeeJobs);
        //End Employee Jobs

        // TODO: 8/30/2018  map payrates

        // TODO: 8/30/2018 map acclvl


        return alohaEmployee;
    }
}
