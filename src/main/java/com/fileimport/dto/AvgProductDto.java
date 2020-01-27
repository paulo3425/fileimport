package com.fileimport.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Builder
@Data
@AllArgsConstructor
public class AvgProductDto {

    public AvgProductDto(){
        
    }

    private String name;
    private BigDecimal avgPrice;
    private Integer quantity;
    private BigDecimal totalPrice;
}
