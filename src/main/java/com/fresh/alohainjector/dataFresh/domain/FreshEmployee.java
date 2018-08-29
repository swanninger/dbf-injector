package com.fresh.alohainjector.dataFresh.domain;

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

    private String img_name;

    private Short bohSecurity;
}
