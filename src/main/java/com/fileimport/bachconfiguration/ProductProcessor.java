package com.fileimport.bachconfiguration;

import com.fileimport.dto.PayloadDto;
import com.fileimport.model.Industry;
import com.fileimport.model.Product;
import com.fileimport.model.ProductOrigin;
import com.fileimport.model.ProductType;
import org.springframework.batch.item.ItemProcessor;

import java.math.BigDecimal;

public class ProductProcessor<T, Y> implements ItemProcessor<PayloadDto, Product> {

//    @Autowired
//    private ProductRepository productRepository;
//
//    @Autowired
//    private IndustryRepository industryRepository;
//
//    @Autowired
//    private ProductOriginRepository productOriginRepository;
//
//    @Autowired
//    private ProductTypeRepository productTypeRepository;


    @Override
    public Product process(PayloadDto p) throws Exception {

//        ProductType productType = productTypeRepository.findByName(p.getType());
//        Industry industry = industryRepository.findByName(p.getIndustry());
//        ProductOrigin productOrigin = productOriginRepository.findByName(p.getOrigin());
//
//        if(productType==null)
//            productType = ProductType.builder().name(p.getType()).build();
//        if(industry ==null)
//            industry = Industry.builder().name(p.getIndustry()).build();
//        if(productOrigin == null)
//            productOrigin = ProductOrigin.builder().name(p.getOrigin()).build();


         return Product.builder()
                .id(p.getProduct())
                .quantity(p.getQuantity())
                .price(new BigDecimal(p.getPrice()))
                .productType(ProductType.builder().name(p.getType()).build())
                .industry(Industry.builder().name(p.getIndustry()).build())
                .productOrigin(ProductOrigin.builder().name(p.getOrigin()).build())
                .build();
    }
}
