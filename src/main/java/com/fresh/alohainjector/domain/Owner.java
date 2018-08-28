package com.fresh.alohainjector.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Owner {

    @Id
    @Type(type="uuid-char")
//    @Column( columnDefinition = "uuid", updatable = false )
    private UUID id;

    private String name;
    private int number;
    private int ownerType;

//    @OneToMany(mappedBy = "owner")
//    private List<Employee> employees;
}
