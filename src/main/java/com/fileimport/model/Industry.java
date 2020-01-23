package com.fileimport.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "industry")
@SequenceGenerator(name = "industry_seq", initialValue = 1, allocationSize = 1)
public class Industry {

    public Industry(){

    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "industry_seq")
    private Integer id;

    @Column(name = "name", length = 100, unique = true)
    private String name;

}
