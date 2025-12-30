package com.city.smart.mobility_rest_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Data 
@AllArgsConstructor 
@NoArgsConstructor
@Entity
public class Ligne {
    @Id
    private Long id;
    private String nom; // Ex: "Ligne 17A"
    private String typeTransport; // "bus", "métro", "train"
    private String statut; // "Normal", "Retard", "Perturbation"
}