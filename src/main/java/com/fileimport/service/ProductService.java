package com.fileimport.service;

import com.fileimport.dto.AvgProductDto;
import com.fileimport.model.Industry;
import com.fileimport.model.Product;
import com.fileimport.model.ProductOrigin;
import com.fileimport.model.ProductType;
import com.fileimport.repository.IndustryRepository;
import com.fileimport.repository.ProductOriginRepository;
import com.fileimport.repository.ProductRepository;
import com.fileimport.repository.ProductTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private IndustryRepository industryRepository;

    @Autowired
    private ProductOriginRepository productOriginRepository;

    @Autowired
    private ProductTypeRepository productTypeRepository;

    @Transactional
    public void batchSave(List<? extends Product> products) {

        products.stream().forEach(item -> {
            ProductType productType = productTypeRepository.findByName(item.getProductType().getName());
            Industry industry = industryRepository.findByName(item.getIndustry().getName());
            ProductOrigin productOrigin = productOriginRepository.findByName(item.getProductOrigin().getName());
            Product product = productRepository.getById(item.getId());

            if (productType == null)
                productType = item.getProductType();
            if (industry == null)
                industry = item.getIndustry();
            if (productOrigin == null)
                productOrigin = item.getProductOrigin();

            if (product == null) {
                item.setProductType(productType);
                item.setIndustry(industry);
                item.setProductOrigin(productOrigin);
                item.setTotalPrice(item.getPrice().multiply(new BigDecimal(item.getQuantity())));
                productRepository.save(item);
            } else {
                product.setPrice(this.calcAvgPrice(product, item));
                product.setQuantity(product.getQuantity() + item.getQuantity());
                product.setTotalPrice(product.getTotalPrice().add(item.getPrice().multiply(new BigDecimal(item.getQuantity()))));
                productRepository.save(product);
            }

        });

    }

    private BigDecimal calcAvgPrice(final Product savedProduct, final Product newProduct) {

        int totalQty = savedProduct.getQuantity() + newProduct.getQuantity();
        int qty1 = savedProduct.getQuantity();
        int qty2 = newProduct.getQuantity();
        BigDecimal price1 = savedProduct.getPrice();
        BigDecimal price2 = newProduct.getPrice();
        BigDecimal totalValor1 = price1.multiply(new BigDecimal(qty1));
        BigDecimal totalValor2 = price2.multiply(new BigDecimal(qty2));
        BigDecimal total = totalValor1.add(totalValor2);


        return total.divide(new BigDecimal(totalQty), 9, RoundingMode.DOWN);
    }

    public List<AvgProductDto> getPriceAvg(final String name, final int lojistas) {

        Product product = this.productRepository.getById(name);

        List<AvgProductDto> products = new ArrayList<>();

        if (product == null)
            return null;

        BigDecimal totalCalc = product.getPrice().multiply(new BigDecimal(product.getQuantity()));
        BigDecimal totalToDivide = totalCalc.subtract(product.getTotalPrice());
        BigDecimal totalDivided = totalToDivide.divide(new BigDecimal(product.getQuantity()), 9, RoundingMode.HALF_DOWN);
        BigDecimal avgPrice = product.getPrice().subtract(totalDivided);
        Integer quantityDivide = product.getQuantity() / lojistas;

        BigDecimal totalPrice = avgPrice.multiply(new BigDecimal(product.getQuantity()));
        BigDecimal totalPriceDivided = totalPrice.divide(new BigDecimal(lojistas), 9, RoundingMode.HALF_DOWN);



        for (int count = lojistas; count >= 1; count--)
            products.add(
                    AvgProductDto.builder()
                            .name(name)
                            .avgPrice(avgPrice)
                            .quantity(quantityDivide)
                            .totalPrice(avgPrice.multiply(new BigDecimal(quantityDivide)))
                            .build());

        if (product.getQuantity() % lojistas != 0) {
            products.get(0).setQuantity(quantityDivide + 1);
            products.get(0).setTotalPrice(products.get(0).getTotalPrice().add(avgPrice));
        }

        return products;


    }
}
