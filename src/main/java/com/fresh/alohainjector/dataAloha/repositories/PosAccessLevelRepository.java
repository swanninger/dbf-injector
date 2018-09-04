package com.fresh.alohainjector.dataAloha.repositories;

import com.fresh.alohainjector.dataAloha.domain.PosAccessLevel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PosAccessLevelRepository extends JpaRepository<PosAccessLevel, UUID> {
    Optional<PosAccessLevel> findByNumber(Integer number);
}
