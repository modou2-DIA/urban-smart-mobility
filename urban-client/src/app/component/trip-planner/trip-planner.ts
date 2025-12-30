// DANS urban-angular-client/src/app/trip-planner/trip-planner.component.ts

import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common'; 
import { FormsModule } from '@angular/forms'; 
import { GraphQLService, TripRecommendation } from '../../graphql/graphql';

@Component({
  selector: 'app-trip-planner',
  standalone: true, 
  imports: [CommonModule, FormsModule],
  templateUrl: './trip-planner.html',
  styleUrls: ['./trip-planner.css'] 
})
export class TripPlannerComponent implements OnInit {

  // ===================================
  // I. Modèles d'Entrée & Données Statiques
  // ===================================
  zoneDepartInput: string = 'Downtown';
  zoneArriveeInput: string = 'Financial District';

  availableZones: string[] = [
    'Downtown',
    'Financial District',
    'Old City',
    'Industrial Zone',
    'Residential Area'
  ];

  // ===================================
  // II. Variables d'État et de Sortie (UI State)
  // ===================================
  recommendation: TripRecommendation | null = null;
  loading = false;
  error: string | null = null;
  
  // États de l'Alerte (Devraient idéalement être dans un composant enfant)
  alertStatus: 'INACTIVE' | 'SUBSCRIBED' | 'RECEIVING_DATA' = 'INACTIVE';
  isAlertActive: boolean = false;

  // ===================================
  // III. Cycle de Vie et Injection
  // ===================================
  constructor(private graphqlService: GraphQLService, private cd: ChangeDetectorRef) {}

  ngOnInit() {
    this.fetchRecommendation(this.zoneDepartInput, this.zoneArriveeInput);
  }

  // ===================================
  // IV. Logique Principale (Orchestration du Trajet)
  // ===================================
  fetchRecommendation(start: string, dest: string) {
    if (!start || !dest) {
        this.error = "Veuillez spécifier la zone de départ et d'arrivée.";
        return;
    }
    
    this.loading = true;
    this.error = null;
    this.recommendation = null;
    
    this.graphqlService.getTripRecommendation(start, dest).subscribe({
      next: (data) => {
        this.recommendation = data;
        this.loading = false;
        console.log("Recommandation reçue:", data);
        this.cd.detectChanges();
      },
      error: (err) => {
        this.error = err.message || 'Erreur inconnue lors de la récupération du trajet.';
        this.loading = false;
        this.cd.detectChanges();
      }
    });
  }
  
  // ===================================
  // V. Logique Secondaire (Abonnement gRPC/Alertes)
  // ===================================
  toggleAlertSubscription() {
    if (this.isAlertActive) {
      this.alertStatus = 'SUBSCRIBED';
      // Lancer ici le code d'abonnement réel (appelant un service Web Socket/SSE)
      
      // Simulation pour la démonstration :
      setTimeout(() => {
        if (this.isAlertActive) {
          this.alertStatus = 'RECEIVING_DATA';
        }
      }, 15000); 
      
    } else {
      this.alertStatus = 'INACTIVE';
      // Lancer ici le code de désabonnement réel
    }
  }
}