package com.fresh.dbfinjector.services;

import com.fresh.dbfinjector.dataAloha.domain.Owner;
import com.fresh.dbfinjector.exception.ResourceNotFoundException;
import com.fresh.dbfinjector.dataAloha.repositories.OwnerRepository;
import org.springframework.stereotype.Service;

@Service
public class OwnerServiceImpl implements OwnerService {
    private final OwnerRepository ownerRepository;

    public OwnerServiceImpl(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    @Override
    public Owner getByName(String name) {
        return ownerRepository.findByName(name)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public Owner getByNumber(Integer number) {
        return ownerRepository.findByNumber(number)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public Owner getByOwnerType(Integer ownerType) {
        return ownerRepository.findByOwnerType(ownerType)
                .orElseThrow(ResourceNotFoundException::new);
    }
}
