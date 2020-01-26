package com.fileimport.bachconfiguration;


import com.fileimport.model.Product;
import com.fileimport.service.ProductService;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

public class ProductWrite implements ItemWriter<Product> {

  @Autowired
  private ProductService productService;

    @Override
    public void write(List<? extends Product> list) throws Exception {
        productService.batchSave(list);
    }


}

