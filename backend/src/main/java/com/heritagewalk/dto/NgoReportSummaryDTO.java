package com.heritagewalk.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NgoReportSummaryDTO {

    private String reportTitle;
    private String organizationName;
    private String reportingPeriod;
    private String generatedDate;
    private long totalSitesCovered;
    private long totalReviewsAnalyzed; // Approved reviews
    private long approvedReviewsCount;
    private long pendingReviewsCount;
    private long rejectedReviewsCount;
    private String moderationComplianceNote;
    private double overallAverageRating;
    private Map<Integer, Long> ratingDistribution = new HashMap<>();
    private List<SiteReportSummary> siteSummaries = new ArrayList<>();
    private Map<String, Long> feedbackCategories = new HashMap<>(); // Cleanliness, Maintenance, Signage, Accessibility, Atmosphere
    private List<String> observations = new ArrayList<>();
    private List<String> recommendations = new ArrayList<>();
    private String methodology;

    public static class SiteReportSummary {
        private Long siteId;
        private String siteName;
        private String category;
        private String location;
        private String culturalSignificance;
        private double averageRating;
        private long reviewCount;

        public SiteReportSummary() {
        }

        public SiteReportSummary(Long siteId, String siteName, String category, String location, String culturalSignificance, double averageRating, long reviewCount) {
            this.siteId = siteId;
            this.siteName = siteName;
            this.category = category;
            this.location = location;
            this.culturalSignificance = culturalSignificance;
            this.averageRating = averageRating;
            this.reviewCount = reviewCount;
        }

        public Long getSiteId() {
            return siteId;
        }

        public void setSiteId(Long siteId) {
            this.siteId = siteId;
        }

        public String getSiteName() {
            return siteName;
        }

        public void setSiteName(String siteName) {
            this.siteName = siteName;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getCulturalSignificance() {
            return culturalSignificance;
        }

        public void setCulturalSignificance(String culturalSignificance) {
            this.culturalSignificance = culturalSignificance;
        }

        public double getAverageRating() {
            return averageRating;
        }

        public void setAverageRating(double averageRating) {
            this.averageRating = averageRating;
        }

        public long getReviewCount() {
            return reviewCount;
        }

        public void setReviewCount(long reviewCount) {
            this.reviewCount = reviewCount;
        }
    }

    public NgoReportSummaryDTO() {
    }

    // Getters and Setters
    public String getReportTitle() {
        return reportTitle;
    }

    public void setReportTitle(String reportTitle) {
        this.reportTitle = reportTitle;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getReportingPeriod() {
        return reportingPeriod;
    }

    public void setReportingPeriod(String reportingPeriod) {
        this.reportingPeriod = reportingPeriod;
    }

    public String getGeneratedDate() {
        return generatedDate;
    }

    public void setGeneratedDate(String generatedDate) {
        this.generatedDate = generatedDate;
    }

    public long getTotalSitesCovered() {
        return totalSitesCovered;
    }

    public void setTotalSitesCovered(long totalSitesCovered) {
        this.totalSitesCovered = totalSitesCovered;
    }

    public long getApprovedReviewsCount() {
        return approvedReviewsCount;
    }

    public void setApprovedReviewsCount(long approvedReviewsCount) {
        this.approvedReviewsCount = approvedReviewsCount;
    }

    public long getPendingReviewsCount() {
        return pendingReviewsCount;
    }

    public void setPendingReviewsCount(long pendingReviewsCount) {
        this.pendingReviewsCount = pendingReviewsCount;
    }

    public long getRejectedReviewsCount() {
        return rejectedReviewsCount;
    }

    public void setRejectedReviewsCount(long rejectedReviewsCount) {
        this.rejectedReviewsCount = rejectedReviewsCount;
    }

    public String getModerationComplianceNote() {
        return moderationComplianceNote;
    }

    public void setModerationComplianceNote(String moderationComplianceNote) {
        this.moderationComplianceNote = moderationComplianceNote;
    }

    public long getTotalReviewsAnalyzed() {
        return totalReviewsAnalyzed;
    }

    public void setTotalReviewsAnalyzed(long totalReviewsAnalyzed) {
        this.totalReviewsAnalyzed = totalReviewsAnalyzed;
    }

    public double getOverallAverageRating() {
        return overallAverageRating;
    }

    public void setOverallAverageRating(double overallAverageRating) {
        this.overallAverageRating = overallAverageRating;
    }

    public Map<Integer, Long> getRatingDistribution() {
        return ratingDistribution;
    }

    public void setRatingDistribution(Map<Integer, Long> ratingDistribution) {
        this.ratingDistribution = ratingDistribution;
    }

    public List<SiteReportSummary> getSiteSummaries() {
        return siteSummaries;
    }

    public void setSiteSummaries(List<SiteReportSummary> siteSummaries) {
        this.siteSummaries = siteSummaries;
    }

    public Map<String, Long> getFeedbackCategories() {
        return feedbackCategories;
    }

    public void setFeedbackCategories(Map<String, Long> feedbackCategories) {
        this.feedbackCategories = feedbackCategories;
    }

    public List<String> getObservations() {
        return observations;
    }

    public void setObservations(List<String> observations) {
        this.observations = observations;
    }

    public List<String> getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(List<String> recommendations) {
        this.recommendations = recommendations;
    }

    public String getMethodology() {
        return methodology;
    }

    public void setMethodology(String methodology) {
        this.methodology = methodology;
    }
}
