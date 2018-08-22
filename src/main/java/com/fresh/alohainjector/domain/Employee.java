package com.fresh.alohainjector.domain;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "Employee")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Employee {
    @Id
    @Type(type = "uuid-char")
    @GenericGenerator(name = "generator", strategy = "uuid2")
    @Column(name = "Id" , columnDefinition="uniqueidentifier")
    private UUID id;

    private Integer number;
    private String firstName;
    private String lastName;
}
