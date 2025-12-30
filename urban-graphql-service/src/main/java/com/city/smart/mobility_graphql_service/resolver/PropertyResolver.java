package com.city.smart.mobility_graphql_service.resolver;

import com.city.smart.mobility_graphql_service.model.Property;
import com.city.smart.mobility_graphql_service.repository.PropertyRepository;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import java.util.List;

@Controller
public class PropertyResolver {

    private final PropertyRepository propertyRepository;

    public PropertyResolver(PropertyRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
    }

    // Requête pour récupérer toutes les propriétés ou filtrer par ville
    @QueryMapping
    public List<Property> properties(@Argument String city) {
        if (city != null && !city.isBlank()) {
            System.out.println("GraphQL: Fetching properties filtered by city: " + city);
            return propertyRepository.findByCity(city);
        }
        System.out.println("GraphQL: Fetching all properties from DB.");
        return propertyRepository.findAll();
    }
}