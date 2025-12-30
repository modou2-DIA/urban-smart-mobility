package com.city.smart.grpc_service.alert.service;

import com.city.smart.grpc.alert.generated.Alert;
import com.city.smart.grpc.alert.generated.EmergencyServiceGrpc;
import com.city.smart.grpc.alert.generated.StatusRequest;
import com.city.smart.grpc.alert.generated.StatusResponse;
import com.city.smart.grpc.alert.generated.SubscriptionRequest;
import com.city.smart.grpc_service.alert.model.CriticalZone; // NOUVEL IMPORT
import com.city.smart.grpc_service.alert.repository.CriticalZoneRepository; // NOUVEL IMPORT
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.Optional;

@GrpcService
public class EmergencyServiceImpl extends EmergencyServiceGrpc.EmergencyServiceImplBase {

    private final CriticalZoneRepository zoneRepository; // INJECTION

    // 💡 Injection du repository via le constructeur
    public EmergencyServiceImpl(CriticalZoneRepository zoneRepository) {
        this.zoneRepository = zoneRepository;
    }

    @Override
    public void getStatus(StatusRequest request, StreamObserver<StatusResponse> responseObserver) {
        String zoneId = request.getZoneId();
        
        // --- LOGIQUE DE PERSISTANCE (Remplacement de la simulation) ---
        Optional<CriticalZone> zoneOpt = zoneRepository.findByZoneId(zoneId);
        
        String status;
        String incident;
        
        if (zoneOpt.isPresent()) {
            CriticalZone zone = zoneOpt.get();
            status = zone.getCurrentStatus();
            incident = zone.getIncidentMessage();
            System.out.println("[gRPC Server] Status trouvé pour " + zoneId + " en DB: " + status);
        } else {
            // Statut par défaut si la zone n'est pas trouvée
            status = "INCONNU"; 
            incident = "Zone non répertoriée ou statut non mis à jour.";
            System.out.println("[gRPC Server] Status INCONNU pour la zone : " + zoneId);
        }
        // --- FIN DE LA LOGIQUE DE PERSISTANCE ---

        // Construit et envoie la réponse
        StatusResponse response = StatusResponse.newBuilder()
            .setStatus(status)
            .setLastIncident(incident)
            .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

   
    /**
     * [RPC Server Streaming] Implémentation de la méthode StreamAlerts.
     * Nécessaire pour compléter le contrat, même si l'Orchestrateur ne l'utilise pas encore.
     */



    @Override
    public void streamAlerts(SubscriptionRequest request, StreamObserver<Alert> responseObserver) {
        System.out.println("[gRPC Server] Nouvelle souscription pour les zones : " + request.getZoneIdsList());

        // Simulation d'un flux minimal pour éviter l'erreur UNIMPLEMENTED
        try {
            for (String zone : request.getZoneIdsList()) {
                Alert alert = Alert.newBuilder()
                    .setType(Alert.AlertType.POLLUTION_CRITIQUE)
                    .setZoneId(zone)
                    .setMessage("Alerte de test dans " + zone)
                    .setTimestamp(System.currentTimeMillis())
                    .build();

                responseObserver.onNext(alert);
                Thread.sleep(500); // Ralentissement de 500ms
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        responseObserver.onCompleted();
        System.out.println("[gRPC Server] Flux d'alertes terminé.");
    }
}