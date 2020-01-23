package com.fileimport.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Payload {

    private String product;
    private Integer quantity;
    private String price;
    private String type;
    private String industry;
    private String origin;

    public String getPrice(){
        return this.price.replace("$","");
    }
}
