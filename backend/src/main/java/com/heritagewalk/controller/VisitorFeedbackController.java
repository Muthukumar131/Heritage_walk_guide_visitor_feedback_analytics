package com.heritagewalk.controller;

import com.heritagewalk.dto.FeedbackRequestDTO;
import com.heritagewalk.entity.VisitorFeedback;
import com.heritagewalk.service.VisitorFeedbackService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/feedback")
public class VisitorFeedbackController {

    private final VisitorFeedbackService visitorFeedbackService;

    @Autowired
    public VisitorFeedbackController(VisitorFeedbackService visitorFeedbackService) {
        this.visitorFeedbackService = visitorFeedbackService;
    }

    // 1. Get public visitor feedback (Approved reviews only, sorted by newest first)
    @GetMapping
    public ResponseEntity<List<VisitorFeedback>> getAllFeedback() {
        return ResponseEntity.ok(visitorFeedbackService.getPublicFeedback());
    }

    // 2. Get approved feedback for a specific heritage place
    @GetMapping("/place/{placeId}")
    public ResponseEntity<List<VisitorFeedback>> getFeedbackByPlace(@PathVariable Long placeId) {
        return ResponseEntity.ok(visitorFeedbackService.getPublicFeedbackForPlace(placeId));
    }

    // 3. Submit feedback with 5-star rating and written review (Strictly sets PENDING)
    @PostMapping
    public ResponseEntity<VisitorFeedback> submitFeedback(@Valid @RequestBody FeedbackRequestDTO dto) {
        VisitorFeedback saved = visitorFeedbackService.submitFeedback(dto);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    // 4. Get reviews submitted by a specific visitor (My Reviews)
    @GetMapping("/my-reviews")
    public ResponseEntity<List<VisitorFeedback>> getMyReviews(
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) String email) {
        return ResponseEntity.ok(visitorFeedbackService.getMyReviews(userId, email));
    }

    // 5. Update/Edit own review
    @PutMapping("/{id}")
    public ResponseEntity<VisitorFeedback> updateFeedback(
            @PathVariable Long id,
            @RequestParam(required = false) Long userId,
            @RequestBody FeedbackRequestDTO dto) {
        VisitorFeedback updated = visitorFeedbackService.updateFeedback(id, userId, dto.getRating(), dto.getComment());
        return ResponseEntity.ok(updated);
    }

    // 6. Delete own review (or admin deletion)
    @DeleteMapping("/{id}")
    public ResponseEntity<java.util.Map<String, String>> deleteFeedback(
            @PathVariable Long id,
            @RequestParam(required = false) Long userId,
            @RequestParam(defaultValue = "ROLE_VISITOR") String role) {
        visitorFeedbackService.deleteFeedback(id, userId, role);
        java.util.Map<String, String> res = new java.util.HashMap<>();
        res.put("message", "Review deleted successfully.");
        return ResponseEntity.ok(res);
    }

    // 7. Admin Filtered Reviews Search
    @GetMapping("/filter")
    public ResponseEntity<List<VisitorFeedback>> getFilteredFeedback(
            @RequestParam(required = false) Long siteId,
            @RequestParam(required = false) Integer minRating,
            @RequestParam(required = false) @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE) java.time.LocalDate startDate,
            @RequestParam(required = false) @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE) java.time.LocalDate endDate,
            @RequestParam(required = false) String search) {
        return ResponseEntity.ok(visitorFeedbackService.getAdminFilteredReviews(null, siteId, minRating, startDate, endDate, search));
    }

    // 8. Admin Moderation Status update
    @PatchMapping("/{id}/status")
    public ResponseEntity<VisitorFeedback> updateModerationStatus(
            @PathVariable Long id,
            @RequestParam String status) {
        return ResponseEntity.ok(visitorFeedbackService.updateModerationStatus(id, status));
    }
}
