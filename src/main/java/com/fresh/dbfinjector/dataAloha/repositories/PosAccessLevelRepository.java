package com.fresh.dbfinjector.dataAloha.repositories;

import com.fresh.dbfinjector.dataAloha.domain.PosAccessLevel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PosAccessLevelRepository extends JpaRepository<PosAccessLevel, UUID> {
    Optional<PosAccessLevel> findByNumber(Integer number);
}
