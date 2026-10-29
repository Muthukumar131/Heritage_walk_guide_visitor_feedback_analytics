package com.heritagewalk.service;

import com.heritagewalk.entity.HeritagePlace;
import com.heritagewalk.exception.ResourceNotFoundException;
import com.heritagewalk.repository.HeritagePlaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class HeritagePlaceService {

    private final HeritagePlaceRepository heritagePlaceRepository;

    @Autowired
    public HeritagePlaceService(HeritagePlaceRepository heritagePlaceRepository) {
        this.heritagePlaceRepository = heritagePlaceRepository;
    }

    public List<HeritagePlace> getAllPlaces(String search, String category) {
        if (search != null && !search.trim().isEmpty()) {
            return heritagePlaceRepository.searchPlaces(search.trim());
        }
        if (category != null && !category.trim().isEmpty() && !category.equalsIgnoreCase("ALL")) {
            return heritagePlaceRepository.findByCategoryIgnoreCase(category.trim());
        }
        return heritagePlaceRepository.findAll();
    }

    public HeritagePlace getPlaceById(Long id) {
        return heritagePlaceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Heritage place not found with ID: " + id));
    }

    @Transactional
    public HeritagePlace viewPlaceDetails(Long id) {
        HeritagePlace place = getPlaceById(id);
        heritagePlaceRepository.incrementVisitCount(id);
        place.setVisitCount((place.getVisitCount() != null ? place.getVisitCount() : 0) + 1);
        return place;
    }

    public HeritagePlace createPlace(HeritagePlace place) {
        if (place.getStatus() == null || place.getStatus().trim().isEmpty()) {
            place.setStatus("ACTIVE");
        }
        if (place.getVisitCount() == null) {
            place.setVisitCount(0);
        }
        return heritagePlaceRepository.save(place);
    }

    public HeritagePlace updatePlace(Long id, HeritagePlace updatedPlace) {
        HeritagePlace existing = getPlaceById(id);

        existing.setName(updatedPlace.getName());
        existing.setCategory(updatedPlace.getCategory());
        existing.setDescription(updatedPlace.getDescription());
        existing.setHistoricalBackground(updatedPlace.getHistoricalBackground());
        existing.setHistoricalSignificance(updatedPlace.getHistoricalSignificance());
        existing.setYearOrPeriod(updatedPlace.getYearOrPeriod());
        existing.setAddress(updatedPlace.getAddress());
        existing.setLatitude(updatedPlace.getLatitude());
        existing.setLongitude(updatedPlace.getLongitude());
        existing.setImageUrl(updatedPlace.getImageUrl());
        existing.setOpeningTime(updatedPlace.getOpeningTime());
        existing.setClosingTime(updatedPlace.getClosingTime());
        existing.setEntryFee(updatedPlace.getEntryFee());
        if (updatedPlace.getStatus() != null) {
            existing.setStatus(updatedPlace.getStatus());
        }

        return heritagePlaceRepository.save(existing);
    }

    public void deletePlace(Long id) {
        HeritagePlace place = getPlaceById(id);
        heritagePlaceRepository.delete(place);
    }
}
