package com.city.smart.mobility_graphql_service.client; // Assurez-vous du package

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component; // Utilisez RestTemplate pour la simplicité
import org.springframework.web.client.RestTemplate;

import com.city.smart.mobility_graphql_service.model.TripRecommendation;

@Component
public class OrchestrationRestClient {

    private final RestTemplate restTemplate;
    private final String baseUrl;

    // Configuration pour appeler l'orchestrateur REST
    public OrchestrationRestClient(
        RestTemplate restTemplate,
        @Value("${orchestration.rest.host}") String restHost,
        @Value("${orchestration.rest.port}") int restPort) {
        
        this.restTemplate = restTemplate;
        // L'URL de l'Orchestrateur dans Docker
        this.baseUrl = String.format("http://%s:%d/api/v1/recommendation", restHost, restPort);
    }

    // Le modèle TripRecommendation doit être partagé entre les services
    public TripRecommendation getTripRecommendation(String startZone, String destinationZone) { 
        String url = String.format("%s?startZone=%s&destinationZone=%s", baseUrl, startZone, destinationZone);
        
        System.out.println("[GraphQL Gateway] Appel à l'Orchestrateur REST: " + url);
        
        // Exécute l'appel et mappe la réponse JSON sur la classe TripRecommendation
        return restTemplate.getForObject(url, TripRecommendation.class); // Mappage sur le modèle de données de l'orchestrateur
    }
}