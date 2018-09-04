package com.fresh.alohainjector.dataAloha.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "EmployeeJobPayRateChange")
@Data
@ToString(onlyExplicitlyIncluded = true)
public class EmployeeJobPayRateChange {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "FK_Owner")
    @EqualsAndHashCode.Exclude
    private Owner owner;

    @ManyToOne
    @JoinColumn(name = "FK_EmployeeJobId")
    private EmployeeJob employeeJob;

    private Double payRate;

    private LocalDateTime effectiveDate;

    private LocalDateTime modifiedDate;

    private Integer rateReason = 0;
}
