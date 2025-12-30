package com.city.smart.mobility_graphql_service.model; // Ajustez le package

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "urban_properties")
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String address;
    private String city;
    private int areaSqMeters; // Surface en m²
    private double price; // Prix estimé ou de vente
    private String type; // Ex: "Apartment", "House", "Office"
    
    // Constructeur sans ID pour l'insertion (DataInitializer)
    public Property(String address, String city, int areaSqMeters, double price, String type) {
        this.address = address;
        this.city = city;
        this.areaSqMeters = areaSqMeters;
        this.price = price;
        this.type = type;
    }
}