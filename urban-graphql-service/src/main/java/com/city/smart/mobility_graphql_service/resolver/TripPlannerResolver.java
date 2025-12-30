package com.city.smart.mobility_graphql_service.resolver;

import com.city.smart.mobility_graphql_service.client.OrchestrationRestClient; // NOUVEL IMPORT
import com.city.smart.mobility_graphql_service.model.TripRecommendation;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
public class TripPlannerResolver {

    private final OrchestrationRestClient orchestrationClient;

    public TripPlannerResolver(OrchestrationRestClient orchestrationClient) {
        this.orchestrationClient = orchestrationClient;
    }

    @QueryMapping
    public TripRecommendation planTrip( 
            @Argument String zoneDepart, 
            @Argument String zoneArrivee) { 
        
        System.out.println("GraphQL: Requête reçue. Délégation à l'Orchestrateur REST...");
        
        // DÉLÉGATION : Le service GraphQL appelle le service d'Orchestration REST
        return orchestrationClient.getTripRecommendation(zoneDepart, zoneArrivee);
    }
    // Note: Vous conserverez aussi la requête `properties` pour l'accès DB local
}