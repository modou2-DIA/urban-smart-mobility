package com.city.smart.orchestration.service;

import com.city.smart.orchestration.client.AirQualitySoapClient;
import com.city.smart.orchestration.client.EmergencyGrpcClient;
import com.city.smart.orchestration.client.MobilityRestClient;
import com.city.smart.orchestration.model.TripRecommendation;
import com.city.smart.grpc.alert.generated.StatusResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TripOrchestrationServiceTest {

    @Mock
    private EmergencyGrpcClient grpcClient; // Simule le service gRPC
    @Mock
    private AirQualitySoapClient soapClient; // Simule le service SOAP
    @Mock
    private MobilityRestClient restClient; // Simule le service REST

    @InjectMocks
    private TripOrchestrationService service; // Injecte les Mocks dans le service

    private final String START_ZONE = "ZoneA";
    private final String DEST_ZONE = "ZoneB";

    /**
     * Cas 1 : Statut CRITIQUE (ROUGE) - Doit forcer le Métro
     */
    @Test
    void planTrip_ShouldRecommendMetro_WhenCriticalStatus() {
        // Mock gRPC: Retourne un statut ROUGE
        StatusResponse criticalStatus = StatusResponse.newBuilder().setStatus("ROUGE").build();
        when(grpcClient.getZoneStatus(START_ZONE)).thenReturn(criticalStatus);

        // Mock SOAP: Doit être appelé mais la réponse est ignorée par la logique ROUGE
        //when(soapClient.getAirQualityStatus(START_ZONE)).thenReturn("Pollué");

        // Mock REST: Doit être appelé
        //when(restClient.getRouteRecommendation(any(), any())).thenReturn("Itinéraire métro XYZ");
        
        // Exécution
        TripRecommendation recommendation = service.planTrip(START_ZONE, DEST_ZONE);

        // Assertion critique : Le mode de transport doit être forcé
        assertEquals("Métro", recommendation.getRecommendedMode(), 
                     "Le mode doit être Métro en cas d'urgence ROUGE.");
    }
    
    /**
     * Cas 2 : Statut OK, mais Air Pollué - Doit recommander Tramway/Vélo Électrique
     */
    @Test
    void planTrip_ShouldRecommendElectricBike_WhenPolluted() {
        // Mock gRPC: Retourne un statut normal
        StatusResponse okStatus = StatusResponse.newBuilder().setStatus("VERT").build();
        when(grpcClient.getZoneStatus(START_ZONE)).thenReturn(okStatus);

        
        // Mock SOAP: Retourne un air Pollué
        //when(soapClient.getAirQualityStatus(START_ZONE)).thenReturn("Pollué");

        // Mock REST: Doit être appelé
        //when(restClient.getRouteRecommendation(any(), any())).thenReturn("Itinéraire Tram/Vélo ABC");
        
        // Exécution
        TripRecommendation recommendation = service.planTrip(START_ZONE, DEST_ZONE);

        // Assertion critique
        assertEquals("Tramway/Vélo Électrique", recommendation.getRecommendedMode(), 
                     "Le mode doit être Tramway/Vélo Électrique si l'air est pollué.");
    }

    // TODO: Implémenter Cas 3 (Statut OK, Air OK -> Bus/Vélo)
}