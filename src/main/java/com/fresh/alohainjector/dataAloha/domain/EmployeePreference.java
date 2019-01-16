/*
 * Copyright (c) 2019. Scott Wanninger.
 */

package com.fresh.alohainjector.dataAloha.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Data
@Table(name = "EmployeePreference")
@ToString(onlyExplicitlyIncluded = true)
public class EmployeePreference {
    @Id
    @EqualsAndHashCode.Include
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "FK_EmployeeId")
    @EqualsAndHashCode.Exclude
    private AlohaEmployee employee;
}
