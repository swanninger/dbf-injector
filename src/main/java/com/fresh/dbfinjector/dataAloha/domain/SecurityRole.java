package com.fresh.dbfinjector.dataAloha.domain;

import lombok.Data;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@Data
@ToString(onlyExplicitlyIncluded = true)
public class SecurityRole {
    @Id
    private UUID id;
    @ToString.Include
    private Integer number;
}
