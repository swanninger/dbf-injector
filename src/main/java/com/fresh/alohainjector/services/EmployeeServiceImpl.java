package com.fresh.alohainjector.services;

import com.fresh.alohainjector.dataAloha.domain.AlohaEmployee;
import com.fresh.alohainjector.dataAloha.repositories.AlohaEmployeeRepository;
import com.fresh.alohainjector.dataFresh.domain.FreshEmployee;
import com.fresh.alohainjector.dataFresh.repositories.FreshEmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class EmployeeServiceImpl implements EmployeeService{
    private final AlohaEmployeeRepository alohaEmployeeRepository;
    private final FreshEmployeeRepository freshEmployeeRepository;

    public EmployeeServiceImpl(AlohaEmployeeRepository alohaEmployeeRepository, FreshEmployeeRepository freshEmployeeRepository) {
        this.alohaEmployeeRepository = alohaEmployeeRepository;
        this.freshEmployeeRepository = freshEmployeeRepository;
    }



    @Override
    public List<AlohaEmployee> getAllAlohaEmployees(){
        return alohaEmployeeRepository.findAll();
    }

    @Override
    public List<FreshEmployee> getNewFreshEmployees() {
        return freshEmployeeRepository.findAll();
    }

    @Override
    public void saveEmployee(AlohaEmployee alohaEmployee) {
        alohaEmployeeRepository.save(alohaEmployee);
    }
}
