package com.city.smart.mobility_rest_service.config;

import com.city.smart.mobility_rest_service.model.Ligne;
import com.city.smart.mobility_rest_service.repository.LigneRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalTime;
import com.city.smart.mobility_rest_service.model.Horaire;
import com.city.smart.mobility_rest_service.repository.HoraireRepository;


@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initDatabase(LigneRepository ligneRepository, HoraireRepository horaireRepository) {
        return args -> {
            // 1. Initialisation des Lignes
            Ligne ligneA = new Ligne(101L, "Ligne A", "metro", "Normal");
            Ligne ligne17 = new Ligne(202L, "Ligne 17", "bus", "Retard (10 min)");
            Ligne tgv = new Ligne(303L, "TGV Paris", "train", "Normal");

            ligneRepository.saveAll(java.util.List.of(ligneA, ligne17, tgv));
            System.out.println("--- Données Ligne initialisées (3 lignes) ---");

            // 2. Initialisation des Horaires (si l'entité Horaire est liée à une ligne)
            // Comme Horaire n'a pas de lien direct, on simule des entrées :
            horaireRepository.save(new Horaire(LocalTime.of(8, 30), "Arrêt Mairie", "A"));
            horaireRepository.save(new Horaire(LocalTime.of(9, 15), "Arrêt Université", "B"));
            horaireRepository.save(new Horaire(LocalTime.of(10, 0), "Terminus", "A"));
            System.out.println("--- Données Horaire initialisées (3 entrées) ---");
        };
    }
}