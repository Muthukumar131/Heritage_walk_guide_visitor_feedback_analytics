package com.heritagewalk.dto;

import com.heritagewalk.entity.HeritagePlace;
import com.heritagewalk.entity.VisitorFeedback;

import java.util.ArrayList;
import java.util.List;

public class VisitorDashboardDTO {

    private long totalSitesAvailable;
    private long sitesExploredCount;
    private long reviewsSubmittedCount;
    private double averageRatingGiven;
    private List<HeritagePlace> featuredSites = new ArrayList<>();
    private List<HeritagePlace> recentSites = new ArrayList<>();
    private List<VisitorFeedback> myRecentReviews = new ArrayList<>();

    public VisitorDashboardDTO() {
    }

    public long getTotalSitesAvailable() {
        return totalSitesAvailable;
    }

    public void setTotalSitesAvailable(long totalSitesAvailable) {
        this.totalSitesAvailable = totalSitesAvailable;
    }

    public long getSitesExploredCount() {
        return sitesExploredCount;
    }

    public void setSitesExploredCount(long sitesExploredCount) {
        this.sitesExploredCount = sitesExploredCount;
    }

    public long getReviewsSubmittedCount() {
        return reviewsSubmittedCount;
    }

    public void setReviewsSubmittedCount(long reviewsSubmittedCount) {
        this.reviewsSubmittedCount = reviewsSubmittedCount;
    }

    public double getAverageRatingGiven() {
        return averageRatingGiven;
    }

    public void setAverageRatingGiven(double averageRatingGiven) {
        this.averageRatingGiven = averageRatingGiven;
    }

    public List<HeritagePlace> getFeaturedSites() {
        return featuredSites;
    }

    public void setFeaturedSites(List<HeritagePlace> featuredSites) {
        this.featuredSites = featuredSites;
    }

    public List<HeritagePlace> getRecentSites() {
        return recentSites;
    }

    public void setRecentSites(List<HeritagePlace> recentSites) {
        this.recentSites = recentSites;
    }

    public List<VisitorFeedback> getMyRecentReviews() {
        return myRecentReviews;
    }

    public void setMyRecentReviews(List<VisitorFeedback> myRecentReviews) {
        this.myRecentReviews = myRecentReviews;
    }
}
