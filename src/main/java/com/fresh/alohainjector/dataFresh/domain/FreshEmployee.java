package com.fresh.alohainjector.dataFresh.domain;

import com.fresh.alohainjector.dataAloha.domain.JobCode;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.UUID;

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

    private Short bohSecurity;

    private int jobCode1;
    private int jobCode2;
    private int jobCode3;
    private int jobCode4;
    private int jobCode5;
    private int jobCode6;
}
