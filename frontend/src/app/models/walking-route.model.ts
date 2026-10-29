import { RouteStop } from './route-stop.model';

export interface WalkingRoute {
  id?: number;
  name: string;
  description: string;
  distanceKm: number;
  estimatedDuration: string;
  difficultyLevel: string; // Easy, Moderate, Challenging
  startingPoint: string;
  endingPoint: string;
  imageUrl?: string;
  status?: string;
  stops?: RouteStop[];
  createdAt?: string;
  updatedAt?: string;
}
