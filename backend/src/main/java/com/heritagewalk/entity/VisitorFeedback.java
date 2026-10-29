package com.heritagewalk.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "visitor_feedback")
public class VisitorFeedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "heritage_place_id", nullable = false)
    private HeritagePlace heritagePlace;

    @Column(name = "visitor_name", nullable = false)
    private String visitorName;

    @Column(name = "visitor_email")
    private String visitorEmail;

    @Column(nullable = false)
    private Integer rating; // 1 to 5

    @Column(columnDefinition = "TEXT")
    private String comment;

    @Column(name = "visit_date")
    private LocalDate visitDate;

    @Column(name = "user_id")
    private Long userId;

    private String sentiment = "NEUTRAL"; // POSITIVE, NEUTRAL, NEGATIVE

    @Column(nullable = false)
    private String status = "PENDING"; // PENDING, APPROVED, REJECTED

    @Column(name = "moderation_status")
    private String moderationStatus = "PENDING";

    @Column(name = "moderated_by")
    private String moderatedBy;

    @Column(name = "moderated_at")
    private LocalDateTime moderatedAt;

    @Column(name = "moderation_reason", length = 500)
    private String moderationReason;

    @Column(name = "flagged_content")
    private boolean flaggedContent = false;

    @Column(name = "flagged_reason")
    private String flaggedReason;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public VisitorFeedback() {
    }

    public VisitorFeedback(Long id, HeritagePlace heritagePlace, String visitorName, String visitorEmail,
                           Integer rating, String comment, LocalDate visitDate, String sentiment) {
        this.id = id;
        this.heritagePlace = heritagePlace;
        this.visitorName = visitorName;
        this.visitorEmail = visitorEmail;
        this.rating = rating;
        this.comment = comment;
        this.visitDate = visitDate;
        this.sentiment = sentiment;
        this.status = "PENDING";
        this.moderationStatus = "PENDING";
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public HeritagePlace getHeritagePlace() {
        return heritagePlace;
    }

    public void setHeritagePlace(HeritagePlace heritagePlace) {
        this.heritagePlace = heritagePlace;
    }

    public String getVisitorName() {
        return visitorName;
    }

    public void setVisitorName(String visitorName) {
        this.visitorName = visitorName;
    }

    public String getVisitorEmail() {
        return visitorEmail;
    }

    public void setVisitorEmail(String visitorEmail) {
        this.visitorEmail = visitorEmail;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDate getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(LocalDate visitDate) {
        this.visitDate = visitDate;
    }

    public String getSentiment() {
        return sentiment;
    }

    public void setSentiment(String sentiment) {
        this.sentiment = sentiment;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status != null ? status : moderationStatus;
    }

    public void setStatus(String status) {
        this.status = status != null ? status.toUpperCase() : "PENDING";
        this.moderationStatus = this.status;
    }

    public String getModerationStatus() {
        return getStatus();
    }

    public void setModerationStatus(String moderationStatus) {
        setStatus(moderationStatus);
    }

    public String getModeratedBy() {
        return moderatedBy;
    }

    public void setModeratedBy(String moderatedBy) {
        this.moderatedBy = moderatedBy;
    }

    public LocalDateTime getModeratedAt() {
        return moderatedAt;
    }

    public void setModeratedAt(LocalDateTime moderatedAt) {
        this.moderatedAt = moderatedAt;
    }

    public String getModerationReason() {
        return moderationReason;
    }

    public void setModerationReason(String moderationReason) {
        this.moderationReason = moderationReason;
    }

    public boolean isFlaggedContent() {
        return flaggedContent;
    }

    public void setFlaggedContent(boolean flaggedContent) {
        this.flaggedContent = flaggedContent;
    }

    public String getFlaggedReason() {
        return flaggedReason;
    }

    public void setFlaggedReason(String flaggedReason) {
        this.flaggedReason = flaggedReason;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
