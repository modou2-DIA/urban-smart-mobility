// src/app/graphql/graphql.service.ts

import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators'; // <-- ESSENTIEL POUR LE TRAITEMENT DE LA RÉPONSE

// --- Interfaces DTO Côté CLIENT ---
export interface AirQuality {
  status: string;
  advice: string;
}

export interface MobilityData {
  transportType: string;
  durationMinutes: number;
  routeDescription: string;
}

export interface TripRecommendation {
  airQuality: AirQuality;
  suggestedRoute: MobilityData;
}

export interface Property {
  id: string;
  address: string;
  city: string;
  areaSqMeters: number;
  price: number;
  type: string;
}

@Injectable({
  providedIn: 'root',
})
export class GraphQLService {
  private apiUrl = 'http://localhost:8083/graphql';
  private httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
    }),
  };

  constructor(private http: HttpClient) {}

  // DANS urban-angular-client/src/app/services/graphql.service.ts

getTripRecommendation(startZone: string, destinationZone: string): Observable<TripRecommendation> {
    
    // 1. Définition de la Requête (Correction du format de passage des variables)
    const query = `
      query PlanTripQuery($zoneDepart: String!, $zoneArrivee: String!) { 
        planTrip(zoneDepart: $zoneDepart, zoneArrivee: $zoneArrivee) {
          airQuality { status advice }
          suggestedRoute { transportType durationMinutes }
        }
      }
    `;

    // 2. Définition des Variables (Correction du nom des variables)
    const body = {
      query: query,
      // Les noms des clés doivent correspondre aux variables définies dans la chaîne 'query'
      variables: { zoneDepart: startZone, zoneArrivee: destinationZone },
    };

    return this.http.post<any>(this.apiUrl, body, this.httpOptions).pipe(
        map(response => {
            if (response.errors) {
                console.error("GraphQL Errors:", response.errors);
                throw new Error(response.errors[0].message);
            }
            // Retourne l'objet TripRecommendation extrait de 'data.planTrip'
            return response.data.planTrip as TripRecommendation; 
        })
    );
}


  getProperties(city: string = ''): Observable<Property[]> {
    const query = `
      query Properties($city: String) {
        properties(city: $city) {
          id address city areaSqMeters price type
        }
      }
    `;

    const body = {
      query: query,
      variables: { city: city || undefined },
    };

    return this.http.post<any>(this.apiUrl, body, this.httpOptions).pipe(
        map(response => {
            if (response.errors) {
                console.error("GraphQL Errors:", response.errors);
                throw new Error(response.errors[0].message);
            }
            // Retourne le tableau de Property
            return response.data.properties as Property[];
        })
    );
  }
}