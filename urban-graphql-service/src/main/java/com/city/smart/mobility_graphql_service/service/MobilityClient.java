package com.city.smart.mobility_graphql_service.service;

import com.city.smart.mobility_graphql_service.model.MobilityData;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Optional;

@Service
public class MobilityClient {

    private final RestTemplate restTemplate;
    
    // URL du service REST de Mobilité
    private static final String REST_SERVICE_URL = "http://urban-rest-service:8082/api/v1/mobilites/correspondances";

    public MobilityClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    
    // Cette fonction appelle le service REST de mobilité pour la recherche de correspondances
    public MobilityData findRoute(String depart, String arrivee, String airStatus) {
        
        // Construction de l'URL pour l'endpoint /correspondances
        String url = String.format("%s?depart=%s&arrivee=%s", REST_SERVICE_URL, depart, arrivee);
        
        // 1. Appel REST (le service REST retourne une String)
        String routeDescription;
        try {
             routeDescription = restTemplate.getForObject(url, String.class);
        } catch (Exception e) {
             routeDescription = "Erreur de connexion au service Mobilité : " + e.getMessage();
        }

        // 2. Logique d'adaptation du GraphQL (l'orchestrateur prend des décisions)
        String type;
        int duration;
        
        if ("Pollué".equals(airStatus)) {
            type = "Métro (Priorité Air)"; // Changement de suggestion basé sur l'état de l'air
            duration = 35; // Durée ajustée pour le métro
        } else {
            type = "Bus & Marche (Optimale)"; 
            duration = 20; // Durée optimale
        }
        
        // La description de la route est enrichie par la réponse du service REST
        return new MobilityData(type, duration, routeDescription);
    }
}