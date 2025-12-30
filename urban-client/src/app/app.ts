import { Component, signal } from '@angular/core';
import { TripPlannerComponent } from './component/trip-planner/trip-planner';
import { PropertyListComponent } from './component/property-list/property-list';
@Component({
  standalone: true,
  selector: 'app-root',
  imports: [TripPlannerComponent,PropertyListComponent],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected readonly title = signal('urban-client');
}
