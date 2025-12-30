package com.city.smart.mobility_graphql_service.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TripRecommendation {
    
    
    // Données provenant du service REST de Mobilité
    private MobilityData suggestedRoute;
    
    // Données provenant du service SOAP de Qualité de l'Air
    private AirQualityData airQuality;
    
}