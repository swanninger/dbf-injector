package com.fresh.alohainjector.dataAloha.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Type;

import javax.persistence.*;
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

}
