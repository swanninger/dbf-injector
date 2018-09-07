package com.fresh.alohainjector.dataAloha.repositories;

import com.fresh.alohainjector.dataAloha.domain.AlohaJobCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AlohaJobCodeRepository extends JpaRepository<AlohaJobCode, UUID> {
    Optional<AlohaJobCode> findByNumber(Integer number);
}
