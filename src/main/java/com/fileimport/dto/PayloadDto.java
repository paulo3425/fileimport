package com.fileimport.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;


@Data
public class PayloadDto {


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
