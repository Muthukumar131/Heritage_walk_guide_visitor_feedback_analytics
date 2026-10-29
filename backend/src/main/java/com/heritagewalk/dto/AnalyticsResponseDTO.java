package com.heritagewalk.dto;

import com.heritagewalk.entity.HeritagePlace;
import com.heritagewalk.entity.VisitorFeedback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnalyticsResponseDTO {

    private long totalPlaces;
    private long totalRoutes;
    private long totalFeedback;
    private long totalRegisteredVisitors;
    private double overallAverageRating;
    private Map<Integer, Long> ratingDistribution = new HashMap<>();
    private List<PlaceRatingSummary> placeRatings = new ArrayList<>();
    private List<VisitorFeedback> recentFeedback = new ArrayList<>();
    private List<HeritagePlace> popularPlaces = new ArrayList<>();
    private String highestRatedSiteName;
    private double highestRatedScore;
    private String lowestRatedSiteName;
    private double lowestRatedScore;
    private String mostReviewedSiteName;
    private long mostReviewedCount;
    private String leastReviewedSiteName;
    private long leastReviewedCount;
    private Map<String, Long> reviewTrends = new HashMap<>();

    public static class PlaceRatingSummary {
        private Long placeId;
        private String placeName;
        private double averageRating;
        private long reviewCount;

        public PlaceRatingSummary() {
        }

        public PlaceRatingSummary(Long placeId, String placeName, double averageRating, long reviewCount) {
            this.placeId = placeId;
            this.placeName = placeName;
            this.averageRating = averageRating;
            this.reviewCount = reviewCount;
        }

        public Long getPlaceId() {
            return placeId;
        }

        public void setPlaceId(Long placeId) {
            this.placeId = placeId;
        }

        public String getPlaceName() {
            return placeName;
        }

        public void setPlaceName(String placeName) {
            this.placeName = placeName;
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

    public AnalyticsResponseDTO() {
    }

    // Getters and Setters
    public long getTotalPlaces() {
        return totalPlaces;
    }

    public void setTotalPlaces(long totalPlaces) {
        this.totalPlaces = totalPlaces;
    }

    public long getTotalRoutes() {
        return totalRoutes;
    }

    public void setTotalRoutes(long totalRoutes) {
        this.totalRoutes = totalRoutes;
    }

    public long getTotalFeedback() {
        return totalFeedback;
    }

    public void setTotalFeedback(long totalFeedback) {
        this.totalFeedback = totalFeedback;
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

    public List<PlaceRatingSummary> getPlaceRatings() {
        return placeRatings;
    }

    public void setPlaceRatings(List<PlaceRatingSummary> placeRatings) {
        this.placeRatings = placeRatings;
    }

    public List<VisitorFeedback> getRecentFeedback() {
        return recentFeedback;
    }

    public void setRecentFeedback(List<VisitorFeedback> recentFeedback) {
        this.recentFeedback = recentFeedback;
    }

    public List<HeritagePlace> getPopularPlaces() {
        return popularPlaces;
    }

    public void setPopularPlaces(List<HeritagePlace> popularPlaces) {
        this.popularPlaces = popularPlaces;
    }

    public long getTotalRegisteredVisitors() {
        return totalRegisteredVisitors;
    }

    public void setTotalRegisteredVisitors(long totalRegisteredVisitors) {
        this.totalRegisteredVisitors = totalRegisteredVisitors;
    }

    public String getHighestRatedSiteName() {
        return highestRatedSiteName;
    }

    public void setHighestRatedSiteName(String highestRatedSiteName) {
        this.highestRatedSiteName = highestRatedSiteName;
    }

    public double getHighestRatedScore() {
        return highestRatedScore;
    }

    public void setHighestRatedScore(double highestRatedScore) {
        this.highestRatedScore = highestRatedScore;
    }

    public String getLowestRatedSiteName() {
        return lowestRatedSiteName;
    }

    public void setLowestRatedSiteName(String lowestRatedSiteName) {
        this.lowestRatedSiteName = lowestRatedSiteName;
    }

    public double getLowestRatedScore() {
        return lowestRatedScore;
    }

    public void setLowestRatedScore(double lowestRatedScore) {
        this.lowestRatedScore = lowestRatedScore;
    }

    public String getMostReviewedSiteName() {
        return mostReviewedSiteName;
    }

    public void setMostReviewedSiteName(String mostReviewedSiteName) {
        this.mostReviewedSiteName = mostReviewedSiteName;
    }

    public long getMostReviewedCount() {
        return mostReviewedCount;
    }

    public void setMostReviewedCount(long mostReviewedCount) {
        this.mostReviewedCount = mostReviewedCount;
    }

    public String getLeastReviewedSiteName() {
        return leastReviewedSiteName;
    }

    public void setLeastReviewedSiteName(String leastReviewedSiteName) {
        this.leastReviewedSiteName = leastReviewedSiteName;
    }

    public long getLeastReviewedCount() {
        return leastReviewedCount;
    }

    public void setLeastReviewedCount(long leastReviewedCount) {
        this.leastReviewedCount = leastReviewedCount;
    }

    public Map<String, Long> getReviewTrends() {
        return reviewTrends;
    }

    public void setReviewTrends(Map<String, Long> reviewTrends) {
        this.reviewTrends = reviewTrends;
    }
}
