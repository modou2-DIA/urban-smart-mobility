package com.city.smart.mobility_graphql_service.repository; // Ajustez le package

import com.city.smart.mobility_graphql_service.model.Property;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PropertyRepository extends JpaRepository<Property, Long> {
    // Permet de filtrer par ville, une requête typique pour GraphQL
    java.util.List<Property> findByCity(String city);
}