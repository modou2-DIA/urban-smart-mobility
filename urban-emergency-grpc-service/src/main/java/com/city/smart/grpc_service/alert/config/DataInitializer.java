package com.city.smart.grpc_service.alert.config; // Ajustez le package

import com.city.smart.grpc_service.alert.model.CriticalZone;
import com.city.smart.grpc_service.alert.repository.CriticalZoneRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.List;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initEmergencyData(CriticalZoneRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                List<CriticalZone> initialZones = List.of(
                    // Correspond à l'ancienne logique "ZoneA"
                    new CriticalZone("ZONE_A", "Rond-point Central", "ROUGE", "Pollution critique (Pic de PM2.5)"), 
                    
                    // Nouvelle zone
                    new CriticalZone("ZONE_B", "Périphérique Nord", "VERT", "Conditions normales"),
                    
                    // Correspond à l'ancienne logique "ZoneOrange"
                    new CriticalZone("ZONE_C", "Quartier Industriel", "ORANGE", "Légère congestion routière")
                );
                repository.saveAll(initialZones);
                System.out.println("--- Données CriticalZone initialisées (3 zones) ---");
            } else {
                System.out.println("--- Données CriticalZone déjà présentes. Initialisation ignorée. ---");
            }
        };
    }
}