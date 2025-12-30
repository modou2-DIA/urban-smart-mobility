package com.city.smart.mobility_rest_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.city.smart.mobility_rest_service.model.Ligne;


public interface LigneRepository extends JpaRepository<Ligne, Long> {
    
    // Spring Data JPA fournira findById, findAll, save, etc.
}