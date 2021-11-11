package com.smoothstack.utopia.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "airport")
public class Airport {
    @Id
    @NotNull
    @Size(min = 3, max = 3)
    @Column(name = "iata_id")
    private String code;

    @NotNull
    @Size(max = 45)
    private String city;
}
