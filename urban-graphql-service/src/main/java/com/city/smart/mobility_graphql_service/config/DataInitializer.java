package com.city.smart.mobility_graphql_service.config; // Ajustez le package

import com.city.smart.mobility_graphql_service.model.Property;
import com.city.smart.mobility_graphql_service.repository.PropertyRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.List;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initPropertyData(PropertyRepository repository) {
        return args -> {
            // Seulement initialiser si la DB est vide
            if (repository.count() == 0) {
                List<Property> initialProperties = List.of(
                    new Property("15 Rue de la Paix", "CentreVille", 95, 450000.00, "Apartment"),
                    new Property("2 Place du Marché", "CentreVille", 220, 1200000.00, "House"),
                    new Property("45 Av. Tech", "TechPark", 1500, 8500000.00, "Office"),
                    new Property("10 Rue calme", "QuartierNord", 60, 250000.00, "Apartment")
                );
                repository.saveAll(initialProperties);
                System.out.println("--- Données Property initialisées (4 propriétés) ---");
            } else {
                System.out.println("--- Données Property déjà présentes. Initialisation ignorée. ---");
            }
        };
    }
}