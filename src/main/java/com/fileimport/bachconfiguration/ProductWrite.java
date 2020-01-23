package com.fileimport.bachconfiguration;

import com.fileimport.model.Industry;
import com.fileimport.model.Product;
import com.fileimport.model.ProductOrigin;
import com.fileimport.model.ProductType;
import com.fileimport.repository.IndustryRepository;
import com.fileimport.repository.ProductOriginRepository;
import com.fileimport.repository.ProductRepository;
import com.fileimport.repository.ProductTypeRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class ProductWrite implements ItemWriter<Product> {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private IndustryRepository industryRepository;

    @Autowired
    private ProductOriginRepository productOriginRepository;

    @Autowired
    private ProductTypeRepository productTypeRepository;

    @Override
    public void write(List<? extends Product> list) throws Exception {

        try {
            for (final Product item : list) {

                ProductType productType = productTypeRepository.findByName(item.getProductType().getName());
                Industry industry = industryRepository.findByName(item.getIndustry().getName());
                ProductOrigin productOrigin = productOriginRepository.findByName(item.getProductOrigin().getName());
                Product product = productRepository.getById(item.getId());

                BigDecimal aux = BigDecimal.ZERO;

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
                    productRepository.save(item);
                } else {


                    final int totalQty = product.getQuantity() + item.getQuantity();

                    final int qty1 = product.getQuantity();
                    final int qty2 = item.getQuantity();
                    final BigDecimal price1 = product.getPrice();
                    final BigDecimal price2 =item.getPrice();


                    final BigDecimal totalValor1 = price1.multiply(new BigDecimal(qty1));
                    final BigDecimal totalValor2 = price2.multiply(new BigDecimal(qty2));
//
//
                    final BigDecimal total = totalValor1.add(totalValor2);



//                    final BigDecimal avgPrice = this.avgPrice(totalQty,total);
                    product.setPrice(this.avgPrice(totalQty,total));

                    product.setQuantity(totalQty);

                    productRepository.save(product);
                }



            }


        } catch (Exception ex) {
            System.out.println(ex);
        }


    }

    private final BigDecimal avgPrice(final int qty, final BigDecimal value){
        final BigDecimal aux = value;

        return aux.setScale(9).divide(new BigDecimal(qty), RoundingMode.DOWN);
    }
}

