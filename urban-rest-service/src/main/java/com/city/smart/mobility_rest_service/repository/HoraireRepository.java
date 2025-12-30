package com.city.smart.mobility_rest_service.repository;

import com.city.smart.mobility_rest_service.model.Horaire;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HoraireRepository extends JpaRepository<Horaire, Long> {
    
    // Ajoutez une méthode de recherche pour simuler la jointure par l'ID de la ligne
    // Bien que ce ne soit pas un lien JPA direct, c'est rapide pour le POC.
    // Vous aurez besoin d'un champ "ligneId" dans Horaire.java pour que cela fonctionne.
    // Pour l'instant, nous allons nous contenter des méthodes de base.
}