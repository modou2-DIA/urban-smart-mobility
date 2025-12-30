package com.city.smart.mobility_rest_service.model;

import java.time.LocalTime;

import jakarta.persistence.*; // Import requis pour les annotations JPA
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data 
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Horaire {
    
    // NOUVELLE CLÉ PRIMAIRE AUTO-GÉNÉRÉE
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long horaireId; // Ajout d'une clé primaire

    private LocalTime heureDepart;
    private String arret;
    private String plateforme;

    public Horaire(LocalTime hd, String arret, String plat)
    {
        this.heureDepart=hd;
        this.arret= arret;
        this.plateforme= plat;
    }


}