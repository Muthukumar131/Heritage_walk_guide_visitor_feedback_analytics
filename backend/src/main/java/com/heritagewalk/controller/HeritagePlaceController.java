package com.heritagewalk.controller;

import com.heritagewalk.entity.HeritagePlace;
import com.heritagewalk.entity.VisitorFeedback;
import com.heritagewalk.service.HeritagePlaceService;
import com.heritagewalk.service.VisitorFeedbackService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/places")
public class HeritagePlaceController {

    private final HeritagePlaceService heritagePlaceService;
    private final VisitorFeedbackService visitorFeedbackService;

    @Autowired
    public HeritagePlaceController(HeritagePlaceService heritagePlaceService,
                                   VisitorFeedbackService visitorFeedbackService) {
        this.heritagePlaceService = heritagePlaceService;
        this.visitorFeedbackService = visitorFeedbackService;
    }

    // 1. Get all places (with optional search and category filter)
    @GetMapping
    public ResponseEntity<List<HeritagePlace>> getAllPlaces(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String category) {
        List<HeritagePlace> places = heritagePlaceService.getAllPlaces(search, category);
        return ResponseEntity.ok(places);
    }

    // 2. Get single place details and track visit count
    @GetMapping("/{id}")
    public ResponseEntity<HeritagePlace> getPlaceById(@PathVariable Long id) {
        HeritagePlace place = heritagePlaceService.viewPlaceDetails(id);
        return ResponseEntity.ok(place);
    }

    // 2.1 Get approved reviews for a specific place (Public)
    @GetMapping("/{id}/reviews")
    public ResponseEntity<List<VisitorFeedback>> getApprovedReviewsForPlace(@PathVariable Long id) {
        return ResponseEntity.ok(visitorFeedbackService.getPublicFeedbackForPlace(id));
    }

    // 3. Create a new heritage place (Admin)
    @PostMapping
    public ResponseEntity<HeritagePlace> createPlace(@Valid @RequestBody HeritagePlace place) {
        HeritagePlace created = heritagePlaceService.createPlace(place);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    // 4. Update an existing heritage place (Admin)
    @PutMapping("/{id}")
    public ResponseEntity<HeritagePlace> updatePlace(@PathVariable Long id, @Valid @RequestBody HeritagePlace place) {
        HeritagePlace updated = heritagePlaceService.updatePlace(id, place);
        return ResponseEntity.ok(updated);
    }

    // 5. Delete a heritage place (Admin)
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deletePlace(@PathVariable Long id) {
        heritagePlaceService.deletePlace(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Heritage place with ID " + id + " was deleted successfully.");
        return ResponseEntity.ok(response);
    }
}
