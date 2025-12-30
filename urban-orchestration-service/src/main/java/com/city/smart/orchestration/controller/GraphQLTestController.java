package com.city.smart.orchestration.controller;

import com.city.smart.orchestration.client.RealEstateGraphQLClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class GraphQLTestController {

    private final RealEstateGraphQLClient graphQLClient;

    public GraphQLTestController(RealEstateGraphQLClient graphQLClient) {
        this.graphQLClient = graphQLClient;
    }

    // Endpoint pour tester le client GraphQL
    @GetMapping("/api/test/graphql")
    public Mono<String> testGraphQLClient(@RequestParam(defaultValue = "Paris") String city) {
        
        System.out.println("TEST GraphQL : Requête pour les propriétés à " + city);
        
        // Appelle la méthode asynchrone du client
        return graphQLClient.getPropertiesByCity(city)
            .onErrorResume(e -> {
                // Gestion d'erreur au niveau Controller si l'appel échoue complètement
                return Mono.just("{\"error\": \"Échec de l'appel GraphQL. Détail: " + e.getMessage() + "\"}");
            });
    }
}