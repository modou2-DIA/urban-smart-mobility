import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common'; 
import { GraphQLService,Property } from '../../graphql/graphql';
@Component({
  standalone: true,
  selector: 'app-property-list',
  imports: [CommonModule],
  templateUrl: './property-list.html',
  styleUrl: './property-list.css',
})
export class PropertyListComponent implements OnInit {
  properties: Property[] = [];
  loading = false;
  error: string | null = null;

  constructor(private graphqlService: GraphQLService) {}

  ngOnInit() {
    this.fetchProperties();
  }

  fetchProperties() {
    this.loading = true;
    this.error = null;
    this.graphqlService.getProperties('CentreVille').subscribe({
      next: (data) => {
        this.properties = data;
        this.loading = false;
      },
      error: (err) => {
        this.error = err.message || 'Erreur inconnue lors de la récupération des propriétés.';
        this.loading = false;
      }
    });
  }
}
