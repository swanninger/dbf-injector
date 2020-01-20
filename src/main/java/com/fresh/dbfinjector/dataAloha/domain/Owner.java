package com.fresh.dbfinjector.dataAloha.domain;

import lombok.Data;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@Data
@ToString(onlyExplicitlyIncluded = true)
public class Owner {

    @Id
//    @Type(type="uuid-char")
//    @Column( columnDefinition = "uuid", updatable = false )
    private UUID id;

    @ToString.Include
    private String name;

    private int number;
    private int ownerType;
}
