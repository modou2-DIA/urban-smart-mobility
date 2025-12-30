package com.city.smart.grpc_service.alert.model; // Ajustez le package

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "critical_zones")
public class CriticalZone {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // Le Zone ID utilisé dans le .proto et dans l'appel gRPC (clé de recherche)
    @Column(unique = true, nullable = false)
    private String zoneId; 
    
    private String name;
    
    // Le statut actuel, ex: "VERT", "ORANGE", "ROUGE"
    private String currentStatus; 
    
    // Un message d'incident si le statut n'est pas VERT
    private String incidentMessage; 
    
    // Constructeur pour l'insertion
    public CriticalZone(String zoneId, String name, String currentStatus, String incidentMessage) {
        this.zoneId = zoneId;
        this.name = name;
        this.currentStatus = currentStatus;
        this.incidentMessage = incidentMessage;
    }
}