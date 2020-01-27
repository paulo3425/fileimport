package com.fileimport.controller;

import com.fileimport.dto.AvgProductDto;
import com.fileimport.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("product")
public class ProductController {

    @Autowired
    private ProductService productService;


    @GetMapping("/{name}/lojistas/{quantity}")
    public ResponseEntity<List<AvgProductDto>> getValue(@PathVariable("name") String name, @PathVariable("quantity") int quantity) {

        List<AvgProductDto> products = this.productService.getPriceAvg(name, quantity);

        if (products == null)
            return new ResponseEntity<List<AvgProductDto>>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<List<AvgProductDto>>(products, HttpStatus.OK);
    }

}
