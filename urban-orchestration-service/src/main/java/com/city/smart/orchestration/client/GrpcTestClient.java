package com.city.smart.orchestration.client;


// Ceci est un exemple de la façon dont le client d'orchestration appelle votre service gRPC

import com.city.smart.grpc.alert.generated.EmergencyServiceGrpc;
import com.city.smart.grpc.alert.generated.StatusRequest;
import com.city.smart.grpc.alert.generated.StatusResponse;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class GrpcTestClient {

    private final EmergencyServiceGrpc.EmergencyServiceBlockingStub blockingStub;

    public GrpcTestClient(String host, int port) {
        // Créez le canal de communication vers le serveur gRPC (port 9090 est le défaut pour gRPC)
        ManagedChannel channel = ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext() // Important pour les tests locaux sans TLS
                .build();
        
        blockingStub = EmergencyServiceGrpc.newBlockingStub(channel);
    }

    public StatusResponse getEmergencyStatus(String zoneId) {
        System.out.println("--- Test gRPC: Requête du statut pour " + zoneId + " ---");
        
        // 1. Remplissage du StatusRequest (C'est la partie que vous demandiez)
        StatusRequest request = StatusRequest.newBuilder()
                .setZoneId(zoneId) 
                .build();

        // 2. Appel au service
        return blockingStub.getStatus(request);
    }

    public static void main(String[] args) {
        // Assurez-vous que votre service gRPC est démarré sur le port 9090 (ou celui configuré)
        GrpcTestClient client = new GrpcTestClient("localhost", 8084); 

        // TEST ROUGE
        StatusResponse responseA = client.getEmergencyStatus("ZONE_A");
        System.out.println("ZONE_A Réponse: " + responseA.getStatus() + " | Incident: " + responseA.getLastIncident());
        
        // TEST VERT
        StatusResponse responseB = client.getEmergencyStatus("ZONE_B");
        System.out.println("ZONE_B Réponse: " + responseB.getStatus() + " | Incident: " + responseB.getLastIncident());

        // TEST INCONNU (pour s'assurer que l'échec est correct)
        StatusResponse responseX = client.getEmergencyStatus("ZONE_X");
        System.out.println("ZONE_X Réponse: " + responseX.getStatus() + " | Incident: " + responseX.getLastIncident());
    }
}