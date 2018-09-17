package com.fresh.alohainjector.dataFresh.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Employee")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public class FreshEmployee {
    @Id
    @Column(name = "recid")
    @GeneratedValue
    private Integer id;

    @EqualsAndHashCode.Include
    @ToString.Include
    @Column(unique=true)
    private String empId;

    @EqualsAndHashCode.Include
    @ToString.Include
    private String fName;

    @EqualsAndHashCode.Include
    @ToString.Include
    private String lName;

    @Column(name = "img_name")
    private String imgName;

    private String ssn;

    private LocalDateTime dtModified;

    private Short bohSecurity;

    private Short termCode;

    private Short jobCode1;
    private Short jobCode2;
    private Short jobCode3;
    private Short jobCode4;
    private Short jobCode5;
    private Short jobCode6;
    private Short jobCode7;
    private Short jobCode8;
    private Short jobCode9;
    private Short jobCode10;

    private Double payRate1;
    private Double payRate2;
    private Double payRate3;
    private Double payRate4;
    private Double payRate5;
    private Double payRate6;
    private Double payRate7;
    private Double payRate8;
    private Double payRate9;
    private Double payRate10;

    private Short accLvl1;
    private Short accLvl2;
    private Short accLvl3;
    private Short accLvl4;
    private Short accLvl5;
    private Short accLvl6;
    private Short accLvl7;
    private Short accLvl8;
    private Short accLvl9;
    private Short accLvl10;
}
