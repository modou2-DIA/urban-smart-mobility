package com.city.smart.airqualitysoapservice.endpoint;



import jakarta.jws.WebService;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import com.city.smart.airqualitysoapservice.model.AirQualityMeasurement;
import com.city.smart.airqualitysoapservice.repository.AirQualityRepository; // NOUVEL IMPORT
import jakarta.jws.WebService;
import org.springframework.stereotype.Service;
import java.util.concurrent.ThreadLocalRandom;

// @Component permet à Spring de gérer ce bean.
@Service
@WebService(
    serviceName = "AirQualityService",
    portName = "AirQualityPort",
    targetNamespace = "http://city.smart/soap/airquality",
    endpointInterface = "com.city.smart.airqualitysoapservice.endpoint.AirQualityEndpoint"
)

public class AirQualityEndpointImpl implements AirQualityEndpoint {

    // --- NOUVELLE INJECTION : Le Repository ---
    private final AirQualityRepository airQualityRepository;

    // Injection via Constructeur
    public AirQualityEndpointImpl(AirQualityRepository airQualityRepository) {
        this.airQualityRepository = airQualityRepository;
    }
    // --- FIN DE L'INJECTION ---

    @Override
    public List<AirQualityMeasurement> getHistoricalData(String zoneName, int daysBack) {
        System.out.println("SOAP Call: Fetching historical data from DB for " + zoneName);
        
        // UTILISATION DE LA BASE DE DONNÉES
        // daysBack est ignoré pour la rapidité du POC (on retourne juste toutes les entrées pour la zone)
        return airQualityRepository.findByArretNameOrderByTimestampDesc(zoneName);
    }

    @Override
    public String getCurrentStatus(String zoneName) {
        // SIMULATION DB LÉGÈRE : Trouver la dernière mesure
        // Si la DB est vide, on retourne une valeur par défaut.
        List<AirQualityMeasurement> latestMeasurements = airQualityRepository.findByArretNameOrderByTimestampDesc(zoneName);

        if (!latestMeasurements.isEmpty()) {
             // Retourne le statut le plus récent
             String status = latestMeasurements.get(0).getStatus();
             
             if ("Pollué".equalsIgnoreCase(status)) {
                 return "ALERTE POLLUTION. AQI > 150. ÉVITER LES DÉPLACEMENTS NON ESSENTIELS.";
             }
             return "Normal. Qualité de l'air : " + status;

        } else {
             // Retourne un statut alerte/normal aléatoire si aucune donnée n'est trouvée pour simuler la complexité
             boolean isPolluted = ThreadLocalRandom.current().nextBoolean();
             return isPolluted ? "ALERTE. Données DB manquantes, risque élevé." : "Normal (par défaut).";
        }
    }
}