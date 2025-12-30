package com.city.smart.mobility_rest_service.service;

import java.util.List;
import java.util.Optional; // NOUVEL IMPORT

import org.springframework.stereotype.Service; // NOUVEL IMPORT

import com.city.smart.mobility_rest_service.model.Horaire;
import com.city.smart.mobility_rest_service.model.Ligne;
import com.city.smart.mobility_rest_service.repository.HoraireRepository;
import com.city.smart.mobility_rest_service.repository.LigneRepository;

@Service
public class MobilityService {
    
    // --- NOUVELLE INJECTION : Les Repositories ---
    private final LigneRepository ligneRepository;
    private final HoraireRepository horaireRepository;
    
    // Injection via Constructeur
    public MobilityService(LigneRepository ligneRepository, HoraireRepository horaireRepository) {
        this.ligneRepository = ligneRepository;
        this.horaireRepository = horaireRepository;
    }

    
    // Endpoint 1: Récupération de l'état général du trafic
    public List<Ligne> getTraficStatus() {
        // UTILISATION DE LA BASE DE DONNÉES
        return ligneRepository.findAll();
    }

    // Endpoint 2: Récupération des horaires pour une ligne
    public List<Horaire> getHorairesParLigne(Long ligneId) {
        // On vérifie d'abord si la ligne existe dans la DB
        Optional<Ligne> ligne = ligneRepository.findById(ligneId); 
        
        if (ligne.isPresent()) {
             // 💡 SIMPLIFICATION : Pour le POC rapide, on renvoie TOUS les horaires
             // car la jointure est complexe et prend du temps à coder.
             return horaireRepository.findAll(); 
        }
        return List.of(); 
    }
    // Endpoint 3: Recherche de correspondances (RENDU DYNAMIQUE)
 public String findCorrespondances(String depart, String arrivee) {
 // Logique de simulation déterministe
 String transportType;
 int durationMinutes;
 String routeDescription;

// Cas 1 : Courte distance (Downtown vers Financial District)
 if (("Downtown".equalsIgnoreCase(depart) && "Financial District".equalsIgnoreCase(arrivee)) ||
 ("Financial District".equalsIgnoreCase(depart) && "Downtown".equalsIgnoreCase(arrivee))) {
    transportType = "Vélo/Marche Rapide";
    durationMinutes = 20;
    routeDescription = "Piste cyclable le long du parc central. Très efficace.";
 } 
 // Cas 2 : Longue distance (Industrial Zone vers Old City)
else if (("Industrial Zone".equalsIgnoreCase(depart) && "Old City".equalsIgnoreCase(arrivee)) ||("Old City".equalsIgnoreCase(depart) && "Industrial Zone".equalsIgnoreCase(arrivee))) 
{
    transportType = "Train Express";durationMinutes = 75;
     routeDescription = "Nécessite le train express (Ligne TGV) avec correspondance Bus/Tramway."; 
    }
else {
     transportType = "Bus/Métro (Combiné)";
    durationMinutes = 45;
    routeDescription = "Bus Ligne 17 vers Métro Ligne A. Temps d'attente à prévoir."; 
}

 return String.format("Type:%s|Duration:%d|Description:%s", transportType, durationMinutes, routeDescription);
  }

} 


