package com.city.smart.airqualitysoapservice.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity // C'est une entité JPA
@Table(name = "air_quality_measurements")
public class AirQualityMeasurement {
    
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long measurementId; // Clé primaire technique

    private String arretName; 
    
    // Lombok est utilisé, mais le type de données 'LocalDateTime' est géré par JPA
    private LocalDateTime timestamp; 
    
    private int aqiIndex;
    private String status; 
    private double pm25; 
    public AirQualityMeasurement(String arrname, LocalDateTime date, int index, String status, double pm25)
    {
        this.arretName=arrname;
        this.timestamp= date;
        this.aqiIndex= index;
        this.status=status;
        this.pm25= pm25;
    }
}