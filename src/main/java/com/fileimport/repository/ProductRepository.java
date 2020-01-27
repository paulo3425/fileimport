package com.fileimport.repository;

import com.fileimport.model.Industry;
import com.fileimport.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {

    public Product getById(String id);



}
