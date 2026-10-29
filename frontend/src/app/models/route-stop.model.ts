import { HeritagePlace } from './heritage-place.model';

export interface RouteStop {
  id?: number;
  routeId?: number;
  heritagePlace: HeritagePlace;
  sequenceOrder: number;
  stopNotes?: string;
}

export interface RouteStopDTO {
  heritagePlaceId: number;
  sequenceOrder: number;
  stopNotes?: string;
}
