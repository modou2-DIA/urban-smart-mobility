package com.city.smart.orchestration.service;

import com.city.smart.orchestration.client.AirQualitySoapClient;
import com.city.smart.orchestration.client.EmergencyGrpcClient;
import com.city.smart.orchestration.client.MobilityRestClient;
import com.city.smart.orchestration.model.AirQualityData;
import com.city.smart.orchestration.model.MobilityData;
import com.city.smart.orchestration.model.TripRecommendation;

import com.city.smart.grpc.alert.generated.StatusResponse; // Exemple d'import gRPC
// Importez vos classes SOAP ici

import org.springframework.stereotype.Service;

@Service
public class TripOrchestrationService {

    private final EmergencyGrpcClient grpcClient;
    private final AirQualitySoapClient soapClient;
    private final MobilityRestClient restClient;

    public TripOrchestrationService(EmergencyGrpcClient grpcClient, AirQualitySoapClient soapClient, MobilityRestClient restClient) {
        this.grpcClient = grpcClient;
        this.soapClient = soapClient;
        this.restClient = restClient;
    }

    /**
     * Modélise le processus métier de planification de trajet intelligent.
     */
    public TripRecommendation planTrip(String startZone, String destinationZone) {
        System.out.println("\n===== 🧠 Démarrage de l'Orchestration pour le trajet : " + startZone + " =====");

        // --- 1. Étape gRPC (Urgence) : Priorité Haute ---
        // Vérifie l'état d'urgence pour la zone de départ
        StatusResponse grpcStatus = grpcClient.getZoneStatus(startZone);
        String status = grpcStatus.getStatus(); 
        boolean isCritical = "ROUGE".equalsIgnoreCase(status);
        System.out.println("-> gRPC Urgence : Statut " + status + " détecté.");

        // --- 2. Étape SOAP (Qualité de l'Air) : Seconde priorité ---
        
        // Simule l'appel SOAP pour obtenir l'indice de qualité de l'air
        AirQualityData airData = soapClient.getAirQualityStatus(startZone); 
        
        boolean isPolluted = "Pollué".equalsIgnoreCase(airData.getStatus()); // Uti
        System.out.println("-> SOAP Qualité de l'Air : " + airData.toString() + " pour la zone.");

        // --- 3. LOGIQUE DÉCISIONNELLE DE L'ORCHESTRATEUR ---
        String recommendedMode;
        if (isCritical) {
            recommendedMode = "Métro"; // Forcer le transport souterrain
            System.out.println("-> Décision : Urgence (ROUGE), recommandation forcée : " + recommendedMode);
        } else if (isPolluted) {
            recommendedMode = "Tramway/Vélo Électrique"; // Éviter la pollution de surface
            System.out.println("-> Décision : Air Pollué, recommandation : " + recommendedMode);
        } else {
            recommendedMode = "Bus/Vélo";
            System.out.println("-> Décision : Conditions optimales, recommandation : " + recommendedMode);
        }

        // --- 4. Étape REST (Mobilité) : Obtention du trajet final ---
        // Appel du service REST pour obtenir l'itinéraire selon la recommandationString destinationZone
        MobilityData mobilityData = restClient.getRouteRecommendation(startZone,destinationZone, recommendedMode);
        System.out.println("-> REST Mobilité : Route trouvée : " + mobilityData.getRouteDescription()); // Utilisez le DTO
        System.out.println("===== ✅ Orchestration Terminée =====");

        // --- 5. Retour de la réponse agrégée ---
        return new TripRecommendation(mobilityData, airData, grpcStatus, recommendedMode);
    }
}