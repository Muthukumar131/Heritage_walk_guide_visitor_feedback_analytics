package com.heritagewalk.controller;

import com.heritagewalk.dto.ModerationActionDTO;
import com.heritagewalk.dto.ReviewModerationSummaryDTO;
import com.heritagewalk.entity.VisitorFeedback;
import com.heritagewalk.service.VisitorFeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/reviews")
public class AdminReviewController {

    private final VisitorFeedbackService visitorFeedbackService;

    @Autowired
    public AdminReviewController(VisitorFeedbackService visitorFeedbackService) {
        this.visitorFeedbackService = visitorFeedbackService;
    }

    // 1. Retrieve reviews for moderation with flexible filters
    @GetMapping
    public ResponseEntity<List<VisitorFeedback>> getReviewsForModeration(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) Long siteId,
            @RequestParam(required = false) Integer minRating,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(required = false) String search) {
        List<VisitorFeedback> reviews = visitorFeedbackService.getAdminFilteredReviews(status, siteId, minRating, startDate, endDate, search);
        return ResponseEntity.ok(reviews);
    }

    // 2. Retrieve moderation counts (Pending, Approved, Rejected, Total)
    @GetMapping("/summary")
    public ResponseEntity<ReviewModerationSummaryDTO> getModerationSummary() {
        return ResponseEntity.ok(visitorFeedbackService.getModerationSummary());
    }

    // 3. Approve a review
    @PatchMapping("/{id}/approve")
    public ResponseEntity<VisitorFeedback> approveReview(
            @PathVariable Long id,
            @RequestParam(required = false, defaultValue = "admin") String moderator) {
        VisitorFeedback approved = visitorFeedbackService.approveReview(id, moderator);
        return ResponseEntity.ok(approved);
    }

    // 4. Reject a review with optional reason
    @PatchMapping("/{id}/reject")
    public ResponseEntity<VisitorFeedback> rejectReview(
            @PathVariable Long id,
            @RequestParam(required = false, defaultValue = "admin") String moderator,
            @RequestParam(required = false) String reason,
            @RequestBody(required = false) ModerationActionDTO body) {
        String finalReason = reason;
        if ((finalReason == null || finalReason.trim().isEmpty()) && body != null) {
            finalReason = body.getReason();
        }
        VisitorFeedback rejected = visitorFeedbackService.rejectReview(id, moderator, finalReason);
        return ResponseEntity.ok(rejected);
    }

    // 5. Delete review as administrator
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteReviewAdmin(@PathVariable Long id) {
        visitorFeedbackService.deleteFeedback(id, null, "ROLE_ADMIN");
        Map<String, String> res = new HashMap<>();
        res.put("message", "Review deleted successfully by administrator.");
        return ResponseEntity.ok(res);
    }
}
