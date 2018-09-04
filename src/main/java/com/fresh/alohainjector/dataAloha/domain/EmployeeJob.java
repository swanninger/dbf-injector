package com.fresh.alohainjector.dataAloha.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "EmployeeJob")
@Data
@ToString(onlyExplicitlyIncluded = true)
public class EmployeeJob {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "FK_EmployeeID")
    private AlohaEmployee employee;

    @ManyToOne
    @JoinColumn(name = "FK_JobCode")
    @ToString.Include
    private AlohaJobCode alohaJobCode;

    @OneToMany(mappedBy = "EmployeeJob")
    private EmployeeJobPayRateChange employeeJobPayRateChange;

    @ManyToOne
    @JoinColumn(name = "FK_Owner")
    @EqualsAndHashCode.Exclude
    private Owner owner;

    private int sortOrder;

    private boolean primaryJob = false;
}
