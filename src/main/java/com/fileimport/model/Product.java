package com.fileimport.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "product")
public class Product {

    public Product(){

    }

    @Id
    @Column(name = "id", length = 20)
    private String id;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;


    @Column(name = "price", nullable = false, scale = 9)
    private BigDecimal price;

    @OneToOne(fetch = FetchType.EAGER,cascade=CascadeType.ALL)
    @JoinColumn(name = "productType_id", referencedColumnName = "id", nullable = false)
    private ProductType productType;

    @OneToOne(fetch = FetchType.EAGER,cascade=CascadeType.ALL)
    @JoinColumn(name = "industry_id", referencedColumnName = "id", nullable = false)
    private Industry industry;

    @OneToOne(fetch = FetchType.EAGER,cascade=CascadeType.ALL)
    @JoinColumn(name = "productOrigin_id", referencedColumnName = "id", nullable = false)
    private ProductOrigin productOrigin;
}
