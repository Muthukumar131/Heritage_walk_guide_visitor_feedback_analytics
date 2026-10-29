package com.heritagewalk.service;

import com.heritagewalk.dto.FeedbackRequestDTO;
import com.heritagewalk.dto.ReviewModerationSummaryDTO;
import com.heritagewalk.entity.HeritagePlace;
import com.heritagewalk.entity.ReviewStatus;
import com.heritagewalk.entity.VisitorFeedback;
import com.heritagewalk.exception.ResourceNotFoundException;
import com.heritagewalk.repository.HeritagePlaceRepository;
import com.heritagewalk.repository.VisitorFeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class VisitorFeedbackService {

    private final VisitorFeedbackRepository feedbackRepository;
    private final HeritagePlaceRepository heritagePlaceRepository;
    private final ContentModerationService contentModerationService;

    @Autowired
    public VisitorFeedbackService(VisitorFeedbackRepository feedbackRepository,
                                  HeritagePlaceRepository heritagePlaceRepository,
                                  ContentModerationService contentModerationService) {
        this.feedbackRepository = feedbackRepository;
        this.heritagePlaceRepository = heritagePlaceRepository;
        this.contentModerationService = contentModerationService;
    }

    /**
     * Submits a visitor review.
     * Enforces PENDING status unconditionally regardless of frontend input.
     * Performs content safety checks.
     */
    public VisitorFeedback submitFeedback(FeedbackRequestDTO dto) {
        HeritagePlace place = heritagePlaceRepository.findById(dto.getHeritagePlaceId())
                .orElseThrow(() -> new ResourceNotFoundException("Heritage place not found with ID: " + dto.getHeritagePlaceId()));

        // Server-side length & non-empty validation
        contentModerationService.validateReviewLength(dto.getComment());

        VisitorFeedback feedback = new VisitorFeedback();
        feedback.setHeritagePlace(place);
        feedback.setVisitorName(dto.getVisitorName() != null ? dto.getVisitorName().trim() : "Visitor");
        feedback.setVisitorEmail(dto.getVisitorEmail() != null ? dto.getVisitorEmail().trim() : null);
        feedback.setRating(dto.getRating());
        feedback.setComment(dto.getComment().trim());
        feedback.setVisitDate(dto.getVisitDate() != null ? dto.getVisitDate() : LocalDate.now());
        feedback.setUserId(dto.getUserId());

        // Strictly enforce PENDING status on new submissions
        feedback.setStatus(ReviewStatus.PENDING.name());
        feedback.setModeratedBy(null);
        feedback.setModeratedAt(null);
        feedback.setModerationReason(null);

        // Content safety inspection
        ContentModerationService.SafetyCheckResult safety = contentModerationService.inspectContent(dto.getComment());
        if (safety.isFlagged()) {
            feedback.setFlaggedContent(true);
            feedback.setFlaggedReason(safety.getMessage());
        } else {
            feedback.setFlaggedContent(false);
            feedback.setFlaggedReason(null);
        }

        // Sentiment assessment
        feedback.setSentiment(evaluateSentiment(dto.getRating(), dto.getComment()));

        return feedbackRepository.save(feedback);
    }

    /**
     * Retrieves all reviews submitted by the authenticated visitor (includes PENDING, APPROVED, REJECTED).
     */
    public List<VisitorFeedback> getMyReviews(Long userId, String email) {
        if (userId != null) {
            List<VisitorFeedback> reviews = feedbackRepository.findByUserIdOrderByCreatedAtDesc(userId);
            if (!reviews.isEmpty()) return reviews;
        }
        if (email != null && !email.trim().isEmpty()) {
            return feedbackRepository.findByVisitorEmailOrderByCreatedAtDesc(email.trim());
        }
        return List.of();
    }

    /**
     * Updates an existing review.
     * Resets status back to PENDING for re-moderation by administrator.
     */
    public VisitorFeedback updateFeedback(Long id, Long userId, Integer rating, String comment) {
        VisitorFeedback feedback = feedbackRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found with ID: " + id));

        // Ownership safeguard
        if (userId != null && feedback.getUserId() != null && !feedback.getUserId().equals(userId)) {
            throw new IllegalArgumentException("Unauthorized: You can only edit your own reviews.");
        }

        if (comment != null) {
            contentModerationService.validateReviewLength(comment);
            feedback.setComment(comment.trim());

            ContentModerationService.SafetyCheckResult safety = contentModerationService.inspectContent(comment);
            if (safety.isFlagged()) {
                feedback.setFlaggedContent(true);
                feedback.setFlaggedReason(safety.getMessage());
            } else {
                feedback.setFlaggedContent(false);
                feedback.setFlaggedReason(null);
            }
        }

        if (rating != null && rating >= 1 && rating <= 5) {
            feedback.setRating(rating);
        }

        // Reset status to PENDING whenever review is modified
        feedback.setStatus(ReviewStatus.PENDING.name());
        feedback.setModeratedBy(null);
        feedback.setModeratedAt(null);
        feedback.setModerationReason("Review edited by visitor. Awaiting re-moderation.");
        feedback.setSentiment(evaluateSentiment(feedback.getRating(), feedback.getComment()));

        return feedbackRepository.save(feedback);
    }

    /**
     * Deletes a review with ownership verification or admin authorization.
     */
    public void deleteFeedback(Long id, Long userId, String userRole) {
        VisitorFeedback feedback = feedbackRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found with ID: " + id));

        boolean isAdmin = "ROLE_ADMIN".equalsIgnoreCase(userRole);
        boolean isOwner = userId != null && feedback.getUserId() != null && feedback.getUserId().equals(userId);

        if (!isAdmin && !isOwner) {
            throw new IllegalArgumentException("Unauthorized: You can only delete your own reviews.");
        }

        feedbackRepository.delete(feedback);
    }

    /**
     * Admin action: Approves a pending review.
     */
    public VisitorFeedback approveReview(Long id, String moderatorUsername) {
        VisitorFeedback feedback = feedbackRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found with ID: " + id));

        feedback.setStatus(ReviewStatus.APPROVED.name());
        feedback.setModeratedBy(moderatorUsername != null ? moderatorUsername : "admin");
        feedback.setModeratedAt(LocalDateTime.now());
        feedback.setModerationReason("Approved by administrator");

        return feedbackRepository.save(feedback);
    }

    /**
     * Admin action: Rejects a review with an optional internal reason.
     */
    public VisitorFeedback rejectReview(Long id, String moderatorUsername, String reason) {
        VisitorFeedback feedback = feedbackRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found with ID: " + id));

        feedback.setStatus(ReviewStatus.REJECTED.name());
        feedback.setModeratedBy(moderatorUsername != null ? moderatorUsername : "admin");
        feedback.setModeratedAt(LocalDateTime.now());
        feedback.setModerationReason(reason != null && !reason.trim().isEmpty() ? reason.trim() : "Rejected by administrator");

        return feedbackRepository.save(feedback);
    }

    /**
     * Moderation summary counts: PENDING, APPROVED, REJECTED, TOTAL.
     */
    public ReviewModerationSummaryDTO getModerationSummary() {
        long pending = feedbackRepository.countByStatus(ReviewStatus.PENDING.name());
        long approved = feedbackRepository.countByStatus(ReviewStatus.APPROVED.name());
        long rejected = feedbackRepository.countByStatus(ReviewStatus.REJECTED.name());
        long total = feedbackRepository.count();
        return new ReviewModerationSummaryDTO(pending, approved, rejected, total);
    }

    /**
     * Admin filtered review query.
     */
    public List<VisitorFeedback> getAdminFilteredReviews(String status, Long placeId, Integer minRating,
                                                         LocalDate startDate, LocalDate endDate, String search) {
        return feedbackRepository.findFilteredReviewsForAdmin(status, placeId, minRating, startDate, endDate, search);
    }

    /**
     * Public place page: Returns ONLY APPROVED reviews.
     */
    public List<VisitorFeedback> getPublicFeedbackForPlace(Long placeId) {
        if (!heritagePlaceRepository.existsById(placeId)) {
            throw new ResourceNotFoundException("Heritage place not found with ID: " + placeId);
        }
        return feedbackRepository.findByHeritagePlaceIdAndStatusOrderByCreatedAtDesc(placeId, ReviewStatus.APPROVED.name());
    }

    /**
     * Public feedback feed: Returns ONLY APPROVED reviews.
     */
    public List<VisitorFeedback> getPublicFeedback() {
        return feedbackRepository.findByStatusOrderByCreatedAtDesc(ReviewStatus.APPROVED.name());
    }

    // Backward-compatibility aliases
    public List<VisitorFeedback> getFeedbackForPlace(Long placeId) {
        return getPublicFeedbackForPlace(placeId);
    }

    public List<VisitorFeedback> getAllFeedback() {
        return getPublicFeedback();
    }

    public VisitorFeedback updateModerationStatus(Long id, String status) {
        if ("APPROVED".equalsIgnoreCase(status)) {
            return approveReview(id, "admin");
        } else if ("REJECTED".equalsIgnoreCase(status)) {
            return rejectReview(id, "admin", "Rejected by administrator");
        } else {
            VisitorFeedback f = feedbackRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Review not found with ID: " + id));
            f.setStatus(status.toUpperCase());
            return feedbackRepository.save(f);
        }
    }

    private String evaluateSentiment(int rating, String comment) {
        if (rating >= 4) {
            return "POSITIVE";
        } else if (rating == 3) {
            String lower = comment != null ? comment.toLowerCase() : "";
            if (lower.contains("great") || lower.contains("good") || lower.contains("love") || lower.contains("beautiful")) {
                return "POSITIVE";
            }
            if (lower.contains("bad") || lower.contains("poor") || lower.contains("dirty") || lower.contains("crowded")) {
                return "NEGATIVE";
            }
            return "NEUTRAL";
        } else {
            return "NEGATIVE";
        }
    }
}
