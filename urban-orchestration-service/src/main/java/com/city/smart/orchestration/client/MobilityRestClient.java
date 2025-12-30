package com.city.smart.orchestration.client;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.city.smart.orchestration.model.MobilityData; 

@Component
public class MobilityRestClient {

    private final WebClient webClient;
    
    // Chemin de l'endpoint spécifique pour la correspondance
    // Nous ne mettons PAS le chemin de base ici (ex: /api/v1/mobilites),
    // car il doit être géré soit par le WebClient, soit par la méthode.
    // L'idéal est de mettre l'URL de base dans le WebClient et le chemin spécifique ici.
    private static final String CORRESPONDANCES_PATH = "/api/v1/mobilites/correspondances"; 

    // 1. CORRECTION DU CONSTRUCTEUR : WebClient configuré sur la racine du service
    public MobilityRestClient(
        @Value("${orchestration.rest.host}") String restHost,
        @Value("${orchestration.rest.port}") int restPort) {

        // Construction de l'URL de base : Hôte et Port seulement.
        // ÉVITE le double chemin '/api/v1/mobilites' dans les appels.
        String baseUrl = String.format("http://%s:%d", restHost, restPort); 
        
        System.out.println("✅ REST Client: Configuration de l'URL de base sur " + baseUrl);

        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    /**
     * Appelle le service REST pour obtenir une recommandation de trajet.
     * * @param zoneDepart La zone de départ.
     * @param destinationZone La zone d'arrivée (CORRECTION : Ajout du paramètre).
     * @param mode Le mode suggéré (utilisé pour le retour du DTO).
     */
    // 2. CORRECTION DE LA SIGNATURE : Ajout de destinationZone
    public MobilityData getRouteRecommendation(String zoneDepart, String destinationZone, String mode) {
        
        // Les paramètres sont encodés automatiquement par WebClient si nous utilisons uriBuilder.
        // Le code d'encodage manuel est donc inutile ici, nous le retirons pour la clarté.
        
        String rawResponse;
        
        try {
            // 3. CORRECTION DU CHEMIN D'APPEL : Utilisation du chemin complet /api/v1/mobilites/correspondances
            rawResponse = webClient.get()
                .uri(uriBuilder -> uriBuilder
                    // Utiliser le chemin complet de l'endpoint
                    .path(CORRESPONDANCES_PATH) 
                    // Utiliser les paramètres réels et dynamiques
                    .queryParam("depart", zoneDepart)
                    .queryParam("arrivee", destinationZone) // Utilisation du paramètre dynamique
                    .build())
                .retrieve()
                .bodyToMono(String.class) // On attend une String
                .block(); // Appel bloquant
            
            // 4. LOGIQUE DE PARSING AMÉLIORÉE
            if (rawResponse != null && !rawResponse.isBlank()) {
                 // Supposons une réponse formatée comme : Type:T|Duration:D|Description:R
                 String[] parts = rawResponse.split("\\|");
                 if (parts.length >= 3) {
                     // Utilisation des valeurs si le format est respecté
                     String routeType = parts[0].split(":")[1]; 
                     int duration = Integer.parseInt(parts[1].split(":")[1]);
                     String description = parts[2].split(":")[1];
                     
                     // Retourne le DTO avec le mode SUGGÉRÉ par l'Orchestrateur
                     // et les données de durée/description du service REST.
                     return new MobilityData(mode, duration, description);
                 }
                 
                 // Parsing échoué mais réponse reçue
                 return new MobilityData(mode, 999, "Parsing échoué. Réponse brute non conforme: " + rawResponse);
            }
            
            // Réponse vide inattendue
            return new MobilityData(mode, 999, "Réponse vide inattendue.");
            
        } catch (Exception e) {
            System.err.println("Erreur lors de l'appel REST : " + e.getMessage());
            // Retourner une valeur par défaut en cas d'échec de la connexion
            return new MobilityData(mode, 999, "Service Mobilité injoignable : " + e.getMessage());
        }
    }
}