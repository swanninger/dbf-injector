package com.fresh.alohainjector.dataAloha.domain;

import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "JobCode")
@Data
@ToString(onlyExplicitlyIncluded = true)
public class AlohaJobCode {
    @Id
    @GeneratedValue
    private UUID id;
    @ToString.Include
    private int number;
}
