package com.city.smart.airqualitysoapservice.config;

// ... imports Spring Boot et les modèles/repositories ...

import java.time.LocalDateTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.city.smart.airqualitysoapservice.model.AirQualityMeasurement;
import com.city.smart.airqualitysoapservice.repository.AirQualityRepository;


@Configuration
public class DataInitializer {
    @Bean
    public CommandLineRunner initAirQualityData(AirQualityRepository repository) {
        // Ajout d'une vérification pour ne pas réinitialiser la DB à chaque redémarrage (très important pour ddl-auto: update)
        if (repository.count() == 0) {
            return args -> {
                // 1. Zone "Mairie" (Polluée)
                // RETRAIT DES ID FIXES (1L, 2L, 3L, 4L)
                repository.save(new AirQualityMeasurement( "Mairie", LocalDateTime.now().minusHours(1), 150, "Pollué", 55.2));
                repository.save(new AirQualityMeasurement( "Mairie", LocalDateTime.now().minusHours(2), 140, "Pollué", 48.9));
                
                // 2. Zone "Université" (Saine)
                repository.save(new AirQualityMeasurement( "Université", LocalDateTime.now().minusHours(1), 45, "Sain", 12.1));
                repository.save(new AirQualityMeasurement( "Université", LocalDateTime.now().minusHours(2), 52, "Modéré", 15.5));
                
                System.out.println("--- Données Air Quality initialisées (4 mesures) ---");
            };
        } else {
             // Laisse l'application démarrer si la table contient déjà des données.
             return args -> System.out.println("--- Données Air Quality déjà présentes. Initialisation ignorée. ---");
        }
    }
}