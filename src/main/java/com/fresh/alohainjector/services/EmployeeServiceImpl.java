package com.fresh.alohainjector.services;

import com.fresh.alohainjector.dataAloha.domain.AlohaEmployee;
import com.fresh.alohainjector.dataAloha.domain.Owner;
import com.fresh.alohainjector.dataAloha.repositories.AlohaEmployeeRepository;
import com.fresh.alohainjector.dataFresh.domain.FreshEmployee;
import com.fresh.alohainjector.dataFresh.repositories.FreshEmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@Slf4j
public class EmployeeServiceImpl implements EmployeeService{
    private final AlohaEmployeeRepository alohaEmployeeRepository;
    private final FreshEmployeeRepository freshEmployeeRepository;
    private final OwnerService ownerService;

    public EmployeeServiceImpl(AlohaEmployeeRepository alohaEmployeeRepository, FreshEmployeeRepository freshEmployeeRepository, OwnerService ownerService) {
        this.alohaEmployeeRepository = alohaEmployeeRepository;
        this.freshEmployeeRepository = freshEmployeeRepository;

        this.ownerService = ownerService;
    }



    @Override
    public List<AlohaEmployee> getAllAlohaEmployees(){
        return alohaEmployeeRepository.findAll();
    }

    @Override
    public List<FreshEmployee> getNewFreshEmployees() {
        return freshEmployeeRepository.findAllByImgNameIgnoreCase("NEW");
    }

    @Override
    public void saveAlohaEmployee(AlohaEmployee alohaEmployee) {
        alohaEmployeeRepository.save(alohaEmployee);
    }

    @Override
    public void saveFreshEmployee(FreshEmployee freshEmployee) {
        freshEmployeeRepository.save(freshEmployee);
    }

    @Override
    public void importEmployees() {
        List<FreshEmployee> employees = getNewFreshEmployees();
        Owner owner = ownerService.getByOwnerType(0);

        for (FreshEmployee freshEmployee: employees) {
            try {
                AlohaEmployee alohaEmployee = new AlohaEmployee();
                    alohaEmployee.setNumber(Integer.parseInt(freshEmployee.getEmpId()));
                    alohaEmployee.setBOHUser(freshEmployee.getEmpId());
                    alohaEmployee.setFirstName(freshEmployee.getFName());
                    alohaEmployee.setLastName(freshEmployee.getLName());
                    alohaEmployee.setOwner(owner);
                    saveAlohaEmployee(alohaEmployee);

                    freshEmployee.setImgName("COMPLETED");
                    saveFreshEmployee(freshEmployee);
                    log.info("Employee: " + freshEmployee.getEmpId() + " saved.");
            }
            catch (Exception e){
                log.error("Employee: " + freshEmployee.getEmpId() + " could not be saved.");
            }
        }
    }
}
