package com.fresh.alohainjector.dataAloha.repositories;

import com.fresh.alohainjector.dataAloha.domain.Owner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface OwnerRepository extends JpaRepository<Owner, UUID> {

    Optional<Owner> findByName(String name);

    Optional<Owner> findByNumber(Integer number);

    Optional<Owner> findByOwnerType(Integer ownerType);
}
