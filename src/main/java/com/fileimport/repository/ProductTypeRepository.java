package com.fileimport.repository;

import com.fileimport.model.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductTypeRepository extends JpaRepository<ProductType,Integer> {
    public ProductType findByName(String name);
}
