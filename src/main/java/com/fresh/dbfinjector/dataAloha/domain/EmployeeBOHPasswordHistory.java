/*
 * Copyright (c) 2019. Scott Wanninger.
 */

package com.fresh.dbfinjector.dataAloha.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Data
@Table(name = "EmployeeBOHPasswordHistory")
@ToString(onlyExplicitlyIncluded = true)
public class EmployeeBOHPasswordHistory {
    @Id
    @EqualsAndHashCode.Include
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "FK_EmployeeId")
    @EqualsAndHashCode.Exclude
    private AlohaEmployee employee;
}
