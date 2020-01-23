package com.fileimport.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "productOrigin")
@SequenceGenerator(name = "productOrigin_seq", initialValue = 1, allocationSize = 1)
public class ProductOrigin {

    public ProductOrigin(){

    }
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "productOrigin_seq")
    private Integer id;

    @Column(name = "name", length = 50, unique = true)
    private String name;
}
