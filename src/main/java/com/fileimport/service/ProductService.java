package com.fileimport.service;

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

    private final BigDecimal calcAvgPrice(final Product savedProduct, final Product newProduct) {

        int totalQty = savedProduct.getQuantity() + newProduct.getQuantity();
        int qty1 = savedProduct.getQuantity();
        int qty2 = newProduct.getQuantity();
        BigDecimal price1 = savedProduct.getPrice();
        BigDecimal price2 = newProduct.getPrice();
        BigDecimal totalValor1 = price1.multiply(new BigDecimal(qty1));
        BigDecimal totalValor2 = price2.multiply(new BigDecimal(qty2));
        BigDecimal total = totalValor1.add(totalValor2);


        return total.setScale(9).divide(new BigDecimal(totalQty), RoundingMode.DOWN);
    }

}
