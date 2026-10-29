export interface HeritagePlace {
  id?: number;
  name: string;
  category: string;
  description: string;
  historicalBackground?: string;
  historicalSignificance?: string;
  yearOrPeriod?: string;
  address: string;
  latitude?: number;
  longitude?: number;
  imageUrl?: string;
  openingTime?: string;
  closingTime?: string;
  entryFee?: string;
  status?: string;
  visitCount?: number;
  createdAt?: string;
  updatedAt?: string;
}
