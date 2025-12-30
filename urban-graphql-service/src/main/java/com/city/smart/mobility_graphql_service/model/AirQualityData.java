package com.city.smart.mobility_graphql_service.model;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AirQualityData {
    
    private Integer aqiIndex;
    private String status;
    private String advice; // Conseil basé sur l'état de l'air
    
}