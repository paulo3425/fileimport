package com.fileimport.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Builder
@Data
public class AvgProductDto {

    private String name;
    private BigDecimal avgPrice;
    private Integer quantity;
    private BigDecimal totalPrice;
}
