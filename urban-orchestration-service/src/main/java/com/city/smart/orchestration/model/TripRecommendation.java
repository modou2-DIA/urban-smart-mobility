package com.city.smart.orchestration.model;

import com.city.smart.grpc.alert.generated.StatusResponse;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;



@Getter 
public class TripRecommendation {
    
    @JsonProperty("suggestedRoute")
    private final MobilityData suggestedRoute; 
    @JsonProperty("airQuality")
    private final AirQualityData airQualityData; 
    @JsonIgnore
    private final StatusResponse emergencyStatus; // Plus précis que Object
    private final String recommendedMode; 

    // CONSTRUCTEUR : C'est le chaînon manquant pour initialiser les champs 'final'
    public TripRecommendation(MobilityData mobilityData, AirQualityData airQualityData, StatusResponse emergencyStatus, String finalDecision) {
        this.suggestedRoute = mobilityData;
        this.airQualityData = airQualityData;
        this.emergencyStatus = emergencyStatus;
        this.recommendedMode = finalDecision;
    }
    public String getStatus() {
        return emergencyStatus != null ? emergencyStatus.getStatus() : "INCONNU";
    }

 }