package com.awesome.testing.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "information")
@Entity
@Data
@NoArgsConstructor
public class Information {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String nationality;
    private Integer salary;

    public Information(String name, String nationality, Integer salary) {
        this.name = name;
        this.nationality = nationality;
        this.salary = salary;
    }

}
