package com.heritagewalk.service;

import com.heritagewalk.dto.VisitorDashboardDTO;
import com.heritagewalk.entity.HeritagePlace;
import com.heritagewalk.entity.VisitorFeedback;
import com.heritagewalk.repository.HeritagePlaceRepository;
import com.heritagewalk.repository.VisitorFeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class VisitorDashboardService {

    private final HeritagePlaceRepository placeRepository;
    private final VisitorFeedbackRepository feedbackRepository;

    @Autowired
    public VisitorDashboardService(HeritagePlaceRepository placeRepository,
                                   VisitorFeedbackRepository feedbackRepository) {
        this.placeRepository = placeRepository;
        this.feedbackRepository = feedbackRepository;
    }

    public VisitorDashboardDTO getVisitorDashboard(Long userId, String email) {
        VisitorDashboardDTO dto = new VisitorDashboardDTO();

        // Total sites in system
        dto.setTotalSitesAvailable(placeRepository.count());

        // Featured sites (by visit count)
        List<HeritagePlace> popular = placeRepository.findTop5ByOrderByVisitCountDesc();
        dto.setFeaturedSites(popular);

        // Recent sites (first 3)
        List<HeritagePlace> allPlaces = placeRepository.findAll();
        dto.setRecentSites(allPlaces.stream().limit(4).collect(Collectors.toList()));

        // Reviews submitted by this visitor
        List<VisitorFeedback> myReviews = List.of();
        if (userId != null) {
            myReviews = feedbackRepository.findByUserIdOrderByCreatedAtDesc(userId);
        }
        if (myReviews.isEmpty() && email != null && !email.trim().isEmpty()) {
            myReviews = feedbackRepository.findByVisitorEmailOrderByCreatedAtDesc(email.trim());
        }

        dto.setReviewsSubmittedCount(myReviews.size());
        dto.setMyRecentReviews(myReviews);

        // Calculate unique places explored (reviewed)
        Set<Long> exploredPlaceIds = myReviews.stream()
                .filter(r -> r.getHeritagePlace() != null)
                .map(r -> r.getHeritagePlace().getId())
                .collect(Collectors.toSet());
        dto.setSitesExploredCount(exploredPlaceIds.size());

        // Average rating given by visitor
        if (!myReviews.isEmpty()) {
            double sum = myReviews.stream().mapToInt(VisitorFeedback::getRating).sum();
            double avg = sum / myReviews.size();
            dto.setAverageRatingGiven(BigDecimal.valueOf(avg).setScale(1, RoundingMode.HALF_UP).doubleValue());
        } else {
            dto.setAverageRatingGiven(0.0);
        }

        return dto;
    }
}
