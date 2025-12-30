package com.city.smart.orchestration.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.city.smart.orchestration.model.TripRecommendation;
import com.city.smart.orchestration.service.TripOrchestrationService;

@RestController
public class TripOrchestrationController {

    private final TripOrchestrationService orchestrationService;

    public TripOrchestrationController(TripOrchestrationService orchestrationService) {
        this.orchestrationService = orchestrationService;
    }

    @GetMapping("/api/v1/recommendation")
    public TripRecommendation getTripRecommendation(
            @RequestParam("startZone") String startZone,
            @RequestParam("destinationZone") String destinationZone) {
        
        // L'orchestrateur exécute la logique de décision
        return orchestrationService.planTrip(startZone, destinationZone);
    }
    @GetMapping("/api/v1/info/global")
    public String getGlobalInfo() {
        return "Orchestration Service is Operational and Ready for Global Queries.";
    }
}