package com.fileimport.repository;

import com.fileimport.model.ProductOrigin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductOriginRepository extends JpaRepository<ProductOrigin, Integer> {
    public ProductOrigin findByName(String name);
}
