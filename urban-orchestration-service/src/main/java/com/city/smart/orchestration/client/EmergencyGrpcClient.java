package com.city.smart.orchestration.client;


import com.city.smart.grpc.alert.generated.EmergencyServiceGrpc;
import com.city.smart.grpc.alert.generated.StatusRequest;
import com.city.smart.grpc.alert.generated.StatusResponse;
import org.springframework.stereotype.Component;

@Component
public class EmergencyGrpcClient {

    private final EmergencyServiceGrpc.EmergencyServiceBlockingStub blockingStub;

    // Le Stub est injecté depuis la configuration @Bean ci-dessus
    public EmergencyGrpcClient(EmergencyServiceGrpc.EmergencyServiceBlockingStub blockingStub) {
        this.blockingStub = blockingStub;
    }

    /**
     * Appelle la méthode UNARY GetStatus du service d'Urgence.
     */
 // DANS com.city.smart.orchestration.client.EmergencyGrpcClient.java

public StatusResponse getZoneStatus(String zoneId) {
    // CORRECTION CRITIQUE : Standardiser l'entrée en majuscules
    String standardizedZoneId = zoneId.toUpperCase(); // <--- AJOUTER CETTE LIGNE

    StatusRequest request = StatusRequest.newBuilder()
            .setZoneId(standardizedZoneId) // Utiliser l'ID standardisé
            .build();
    
    System.out.println("gRPC Client: Appel de GetStatus pour ID standardisé : " + standardizedZoneId);

    // Exécution de l'appel gRPC synchrone
    return blockingStub.getStatus(request);
}
    
    
    // Vous ajouteriez ici la logique pour le StreamAlerts (Streaming)
}