package com.fileimport.repository;

import com.fileimport.model.Industry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IndustryRepository extends JpaRepository<Industry, Integer> {

    public Industry findByName(String name);
}
