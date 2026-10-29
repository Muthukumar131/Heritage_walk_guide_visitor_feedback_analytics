package com.heritagewalk.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class FeedbackRequestDTO {

    @NotNull(message = "Heritage Place ID is required")
    private Long heritagePlaceId;

    @NotBlank(message = "Visitor name cannot be blank")
    private String visitorName;

    @Email(message = "Please provide a valid email address")
    private String visitorEmail;

    @NotNull(message = "Rating is required")
    @Min(value = 1, message = "Rating must be at least 1 star")
    @Max(value = 5, message = "Rating cannot exceed 5 stars")
    private Integer rating;

    @NotBlank(message = "Comment or review cannot be blank")
    @jakarta.validation.constraints.Size(min = 5, max = 2000, message = "Comment must be between 5 and 2000 characters")
    private String comment;

    private LocalDate visitDate;

    private Long userId;

    public FeedbackRequestDTO() {
    }

    public FeedbackRequestDTO(Long heritagePlaceId, String visitorName, String visitorEmail, Integer rating, String comment, LocalDate visitDate) {
        this.heritagePlaceId = heritagePlaceId;
        this.visitorName = visitorName;
        this.visitorEmail = visitorEmail;
        this.rating = rating;
        this.comment = comment;
        this.visitDate = visitDate;
    }

    // Getters and Setters
    public Long getHeritagePlaceId() {
        return heritagePlaceId;
    }

    public void setHeritagePlaceId(Long heritagePlaceId) {
        this.heritagePlaceId = heritagePlaceId;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
