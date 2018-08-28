package com.fresh.alohainjector.repositories;

import com.fresh.alohainjector.domain.Owner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OwnerRepository extends JpaRepository<Owner, String> {
    Optional<Owner> findByName(String name);

    Optional<Owner> findByNumber(Integer number);

    Optional<Owner> findByOwnerType(Integer ownerType);
}
