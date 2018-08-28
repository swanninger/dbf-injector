package com.fresh.alohainjector.services;

import com.fresh.alohainjector.domain.Owner;
import com.fresh.alohainjector.exception.ResourceNotFoundException;
import com.fresh.alohainjector.repositories.OwnerRepository;
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
}
