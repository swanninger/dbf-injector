package com.fresh.dbfinjector.dataAloha.repositories;

import com.fresh.dbfinjector.dataAloha.domain.SecurityRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SecurityRoleRepository extends JpaRepository<SecurityRole, UUID> {
    Optional<SecurityRole> findByNumber(Integer number);
}
