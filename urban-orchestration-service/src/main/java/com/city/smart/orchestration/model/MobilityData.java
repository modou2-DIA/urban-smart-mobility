package com.city.smart.orchestration.model;



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

    public MobilityData(String transportType, int durationMinutes, String routeDescription) {
        this.transportType = transportType;
        this.durationMinutes = durationMinutes;
        this.routeDescription = routeDescription;
    }


    
}