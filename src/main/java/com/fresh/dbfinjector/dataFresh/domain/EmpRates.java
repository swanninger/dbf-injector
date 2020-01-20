package com.fresh.dbfinjector.dataFresh.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "EmpRates")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public class EmpRates {
    @Id
    @Column(name = "id")
    @GeneratedValue
    private Integer id;

//    @ManyToOne
//    @JoinColumn(name = "recid")
//    private FreshEmployee employee;
}
