import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { HeritagePlaceService } from '../../../services/heritage-place.service';
import { HeritagePlace } from '../../../models/heritage-place.model';

@Component({
  selector: 'app-place-list',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './place-list.component.html',
  styleUrls: ['./place-list.component.css']
})
export class PlaceListComponent implements OnInit {
  places: HeritagePlace[] = [];
  categories: string[] = [
    'ALL',
    'Fort & Palace',
    'Religious Monument',
    'Tower & Minaret',
    'Mausoleum',
    'Memorial & Civic',
    'Modern Architectural Wonder'
  ];
  selectedCategory = 'ALL';
  searchQuery = '';
  loading = true;
  error = '';

  constructor(private placeService: HeritagePlaceService) {}

  ngOnInit(): void {
    this.fetchPlaces();
  }

  fetchPlaces(): void {
    this.loading = true;
    this.error = '';

    const categoryParam = this.selectedCategory === 'ALL' ? undefined : this.selectedCategory;
    const searchParam = this.searchQuery.trim() === '' ? undefined : this.searchQuery.trim();

    this.placeService.getAllPlaces(searchParam, categoryParam).subscribe({
      next: (data) => {
        this.places = data;
        this.loading = false;
      },
      error: (err) => {
        console.error('Failed to load heritage places', err);
        this.error = 'Unable to connect to the backend API. Please make sure Spring Boot is running on port 8080.';
        this.loading = false;
      }
    });
  }

  filterByCategory(category: string): void {
    this.selectedCategory = category;
    this.fetchPlaces();
  }

  onSearch(): void {
    this.fetchPlaces();
  }

  clearFilters(): void {
    this.searchQuery = '';
    this.selectedCategory = 'ALL';
    this.fetchPlaces();
  }
}
