package com.fresh.alohainjector.dataAloha.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "TerminatedEmployeeJobPayRateChange")
@Data
@ToString(onlyExplicitlyIncluded = true)
public class TerminatedPayRateChange {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "FK_Owner")
    @EqualsAndHashCode.Exclude
    private Owner owner;

    @ManyToOne
    @JoinColumn(name = "FK_EmployeeJobId")
    private TerminatedEmployeeJob employeeJob;

    private Double payRate;

    private LocalDateTime effectiveDate;

    private LocalDateTime modifiedDate;

    private Integer rateReason = 0;
}
