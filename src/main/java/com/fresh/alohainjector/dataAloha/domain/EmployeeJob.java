package com.fresh.alohainjector.dataAloha.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
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
    @JoinColumn(name = "FK_EmployeeId")
    @EqualsAndHashCode.Exclude
    private AlohaEmployee employee;

    @ManyToOne
    @JoinColumn(name = "FK_JobCode")
    @ToString.Include
    private AlohaJobCode alohaJobCode;

    @OneToMany(mappedBy = "employeeJob", cascade = CascadeType.ALL)
    private List<PayRateChange> payRateChanges = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "FK_Owner")
    @EqualsAndHashCode.Exclude
    private Owner owner;

    @ManyToOne
    @JoinColumn(name = "FK_PosAccessLevel")
    @EqualsAndHashCode.Exclude
    private PosAccessLevel posAccessLevel;



    private int sortOrder;

    private boolean primaryJob = false;

    public void addPayRateChange(PayRateChange payRateChange) {
        payRateChanges.add(payRateChange);

        payRateChange.setEmployeeJob(this);
        payRateChange.setOwner(this.owner);
    }
}
