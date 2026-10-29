package com.heritagewalk.controller;

import com.heritagewalk.dto.FeedbackRequestDTO;
import com.heritagewalk.entity.VisitorFeedback;
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
@RequestMapping("/api/reviews")
public class ReviewController {

    private final VisitorFeedbackService visitorFeedbackService;

    @Autowired
    public ReviewController(VisitorFeedbackService visitorFeedbackService) {
        this.visitorFeedbackService = visitorFeedbackService;
    }

    // 1. Submit a review -> Enforces PENDING status unconditionally
    @PostMapping
    public ResponseEntity<VisitorFeedback> submitReview(@Valid @RequestBody FeedbackRequestDTO dto) {
        VisitorFeedback saved = visitorFeedbackService.submitFeedback(dto);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    // 2. Retrieve authenticated visitor's own reviews
    @GetMapping("/my")
    public ResponseEntity<List<VisitorFeedback>> getMyReviews(
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) String email) {
        return ResponseEntity.ok(visitorFeedbackService.getMyReviews(userId, email));
    }

    // 3. Edit visitor's own review -> Resets status to PENDING for re-moderation
    @PutMapping("/{id}")
    public ResponseEntity<VisitorFeedback> updateReview(
            @PathVariable Long id,
            @RequestParam(required = false) Long userId,
            @RequestBody FeedbackRequestDTO dto) {
        VisitorFeedback updated = visitorFeedbackService.updateFeedback(id, userId, dto.getRating(), dto.getComment());
        return ResponseEntity.ok(updated);
    }

    // 4. Delete visitor's own review
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteReview(
            @PathVariable Long id,
            @RequestParam(required = false) Long userId,
            @RequestParam(defaultValue = "ROLE_VISITOR") String role) {
        visitorFeedbackService.deleteFeedback(id, userId, role);
        Map<String, String> res = new HashMap<>();
        res.put("message", "Review deleted successfully.");
        return ResponseEntity.ok(res);
    }
}
