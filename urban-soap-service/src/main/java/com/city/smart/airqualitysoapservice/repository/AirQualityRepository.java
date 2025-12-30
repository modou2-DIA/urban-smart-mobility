package com.city.smart.airqualitysoapservice.repository;

import com.city.smart.airqualitysoapservice.model.AirQualityMeasurement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AirQualityRepository extends JpaRepository<AirQualityMeasurement, Long> {
    
    // AJOUT SIMPLE et RAPIDE : Méthode pour obtenir l'historique par zone.
    // Spring Data JPA génère la requête SQL à partir du nom de la méthode.
    List<AirQualityMeasurement> findByArretNameOrderByTimestampDesc(String arretName);
}