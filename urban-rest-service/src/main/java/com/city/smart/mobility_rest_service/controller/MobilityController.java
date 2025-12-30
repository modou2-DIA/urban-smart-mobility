package com.city.smart.mobility_rest_service.controller;

import com.city.smart.mobility_rest_service.model.Horaire;
import com.city.smart.mobility_rest_service.model.Ligne;
import com.city.smart.mobility_rest_service.service.MobilityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // Indique que c'est un contrôleur REST
@RequestMapping("/api/v1/mobilites") // Base URI pour tous les endpoints du contrôleur
@Tag(name = "Mobilité Intelligente", description = "Gestion des transports publics et du trafic en temps réel.")
public class MobilityController {

    private final MobilityService mobilityService;

    // Injection de dépendance (Spring s'en charge)
    public MobilityController(MobilityService mobilityService) {
        this.mobilityService = mobilityService;
    }

    // --- Endpoint 1: Suivi de l'état du trafic (GET /api/v1/mobilites/trafic/status) ---
    @GetMapping("/trafic/status")
    @Operation(summary = "Récupérer l'état actuel de toutes les lignes de transport.")
    public List<Ligne> getTraficStatus() {
        return mobilityService.getTraficStatus();
    }

    // --- Endpoint 2: Consultation des horaires d'une ligne (GET /api/v1/mobilites/lignes/{ligneId}/horaires) ---
    @GetMapping("/lignes/{ligneId}/horaires")
    @Operation(summary = "Consulter les prochains horaires pour une ligne donnée.")
    public ResponseEntity<List<Horaire>> getHorairesParLigne(@PathVariable Long ligneId) {
        List<Horaire> horaires = mobilityService.getHorairesParLigne(ligneId);
        
        if (horaires.isEmpty()) {
            // Un 404 est plus précis si la ressource parent (la ligne) n'existe pas ou n'a pas d'horaires
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(horaires); // Code 200 OK
    }

    // --- Endpoint 3: Recherche de correspondances (GET /api/v1/mobilites/correspondances) ---
    @GetMapping("/correspondances")
    @Operation(summary = "Rechercher l'itinéraire et les correspondances entre deux arrêts.")
    public String findCorrespondances(@RequestParam String depart, @RequestParam String arrivee) {
        return mobilityService.findCorrespondances(depart, arrivee);
    }
}