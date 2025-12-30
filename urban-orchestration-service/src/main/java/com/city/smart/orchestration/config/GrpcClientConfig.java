package com.city.smart.orchestration.config;


import com.city.smart.grpc.alert.generated.EmergencyServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;

@Configuration
public class GrpcClientConfig {

    // 1. Injection des valeurs depuis application.properties ou l'environnement (Docker)
    @Value("${orchestration.grpc.host}")
    private String GRPC_SERVER_ADDRESS;

    @Value("${orchestration.grpc.port}")
    private int GRPC_SERVER_PORT;
   
    

    @Bean(destroyMethod = "shutdown")
    public ManagedChannel emergencyServiceChannel() {
        // Crée le canal de communication vers le serveur gRPC (port 9090)
        return ManagedChannelBuilder.forAddress(GRPC_SERVER_ADDRESS, GRPC_SERVER_PORT)
                .usePlaintext() // À utiliser UNIQUEMENT pour le DEV, pour la PROD utilisez TLS/SSL
                .build();
    }

    @Bean
    public EmergencyServiceGrpc.EmergencyServiceBlockingStub emergencyServiceBlockingStub(ManagedChannel channel) {
        // Crée un Stub (client synchrone) pour appeler les méthodes gRPC
        return EmergencyServiceGrpc.newBlockingStub(channel);
    }
}