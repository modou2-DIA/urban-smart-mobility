package com.city.smart.grpc_service.alert.repository; // Ajustez le package

import com.city.smart.grpc_service.alert.model.CriticalZone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CriticalZoneRepository extends JpaRepository<CriticalZone, Long> {
    
    // Méthode clé : Trouver une zone par son identifiant unique (utilisé par gRPC)
    Optional<CriticalZone> findByZoneId(String zoneId);
}