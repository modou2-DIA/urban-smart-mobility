package com.city.smart.mobility_graphql_service.model;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MobilityData {
    
    private String transportType;
    private Integer durationMinutes;
    private String routeDescription;
    
}