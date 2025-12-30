package com.city.smart.orchestration.client;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import java.util.Map;

@Component
public class RealEstateGraphQLClient {

    private final WebClient webClient;
    private static final String GRAPHQL_URI = "http://localhost:8083/graphql"; // URL du service GraphQL cible

    public RealEstateGraphQLClient(WebClient.Builder webClientBuilder) {
        // Le client est configuré ici pour l'URL cible
        this.webClient = webClientBuilder.baseUrl(GRAPHQL_URI).build();
    }

    public Mono<String> getPropertiesByCity(String city) {
        
        // 1. Définir la requête GraphQL
        String graphqlQuery = """
                query GetProperties($ville: String) {
                    properties(city: $ville) {
                        address
                        price
                    }
                }
                """;

        // 2. Préparer le corps JSON de la requête POST (standard GraphQL)
        Map<String, Object> body = Map.of(
            "query", graphqlQuery,
            "variables", Map.of("ville", city) // Les variables sont envoyées séparément
        );
        
        // 3. Envoyer la requête via WebClient
        return webClient.post()
            .uri("/") // L'URI de base est déjà définie
            .bodyValue(body)
            .retrieve()
            .bodyToMono(String.class) // Récupération brute pour la démo
            .onErrorResume(e -> {
                 System.err.println("Erreur GraphQL : " + e.getMessage());
                 return Mono.just("{}"); // Fallback en cas d'échec
            });
    }
}