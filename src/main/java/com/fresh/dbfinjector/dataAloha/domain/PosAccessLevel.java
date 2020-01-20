package com.fresh.dbfinjector.dataAloha.domain;

import lombok.Data;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "PosAccessLevel")
@Data
@ToString(onlyExplicitlyIncluded = true)
public class PosAccessLevel {
    @Id
    private UUID id;

    @ToString.Include
    private Integer number;
}
