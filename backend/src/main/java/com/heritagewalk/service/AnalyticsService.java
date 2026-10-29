package com.heritagewalk.service;

import com.heritagewalk.dto.AnalyticsResponseDTO;
import com.heritagewalk.dto.AnalyticsResponseDTO.PlaceRatingSummary;
import com.heritagewalk.entity.HeritagePlace;
import com.heritagewalk.entity.VisitorFeedback;
import com.heritagewalk.repository.HeritagePlaceRepository;
import com.heritagewalk.repository.UserRepository;
import com.heritagewalk.repository.VisitorFeedbackRepository;
import com.heritagewalk.repository.WalkingRouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AnalyticsService {

    private final HeritagePlaceRepository heritagePlaceRepository;
    private final WalkingRouteRepository walkingRouteRepository;
    private final VisitorFeedbackRepository visitorFeedbackRepository;
    private final UserRepository userRepository;

    @Autowired
    public AnalyticsService(HeritagePlaceRepository heritagePlaceRepository,
                            WalkingRouteRepository walkingRouteRepository,
                            VisitorFeedbackRepository visitorFeedbackRepository,
                            UserRepository userRepository) {
        this.heritagePlaceRepository = heritagePlaceRepository;
        this.walkingRouteRepository = walkingRouteRepository;
        this.visitorFeedbackRepository = visitorFeedbackRepository;
        this.userRepository = userRepository;
    }

    public AnalyticsResponseDTO getAnalyticsOverview() {
        AnalyticsResponseDTO response = new AnalyticsResponseDTO();

        // 1. Total counts (Public feedback count counts APPROVED reviews only)
        response.setTotalPlaces(heritagePlaceRepository.count());
        response.setTotalRoutes(walkingRouteRepository.count());
        response.setTotalFeedback(visitorFeedbackRepository.countByStatus("APPROVED"));
        response.setTotalRegisteredVisitors(userRepository.countByRole("ROLE_VISITOR"));

        // 2. Overall average rating (Calculated over APPROVED reviews)
        Double avgRating = visitorFeedbackRepository.calculateOverallAverageRating();
        if (avgRating != null) {
            BigDecimal bd = BigDecimal.valueOf(avgRating).setScale(2, RoundingMode.HALF_UP);
            response.setOverallAverageRating(bd.doubleValue());
        } else {
            response.setOverallAverageRating(0.0);
        }

        // 3. Rating distribution (1 to 5 stars, APPROVED only)
        Map<Integer, Long> distribution = new HashMap<>();
        for (int i = 1; i <= 5; i++) {
            Long count = visitorFeedbackRepository.countApprovedByRating(i);
            distribution.put(i, count != null ? count : 0L);
        }
        response.setRatingDistribution(distribution);

        // 4. Per-place rating aggregates (APPROVED only)
        List<Object[]> aggregates = visitorFeedbackRepository.getPlaceRatingAggregates();
        List<PlaceRatingSummary> placeSummaries = aggregates.stream().map(row -> {
            Long placeId = (Long) row[0];
            String placeName = (String) row[1];
            Double placeAvg = (Double) row[2];
            Long count = (Long) row[3];
            double roundedAvg = (placeAvg != null)
                    ? BigDecimal.valueOf(placeAvg).setScale(2, RoundingMode.HALF_UP).doubleValue()
                    : 0.0;
            return new PlaceRatingSummary(placeId, placeName, roundedAvg, count != null ? count : 0L);
        }).collect(Collectors.toList());
        response.setPlaceRatings(placeSummaries);

        // Calculate highest/lowest rated and most/least reviewed
        if (!placeSummaries.isEmpty()) {
            PlaceRatingSummary highest = placeSummaries.stream()
                    .max(Comparator.comparingDouble(PlaceRatingSummary::getAverageRating))
                    .orElse(null);
            if (highest != null) {
                response.setHighestRatedSiteName(highest.getPlaceName());
                response.setHighestRatedScore(highest.getAverageRating());
            }

            PlaceRatingSummary lowest = placeSummaries.stream()
                    .min(Comparator.comparingDouble(PlaceRatingSummary::getAverageRating))
                    .orElse(null);
            if (lowest != null) {
                response.setLowestRatedSiteName(lowest.getPlaceName());
                response.setLowestRatedScore(lowest.getAverageRating());
            }

            PlaceRatingSummary mostReviewed = placeSummaries.stream()
                    .max(Comparator.comparingLong(PlaceRatingSummary::getReviewCount))
                    .orElse(null);
            if (mostReviewed != null) {
                response.setMostReviewedSiteName(mostReviewed.getPlaceName());
                response.setMostReviewedCount(mostReviewed.getReviewCount());
            }

            PlaceRatingSummary leastReviewed = placeSummaries.stream()
                    .min(Comparator.comparingLong(PlaceRatingSummary::getReviewCount))
                    .orElse(null);
            if (leastReviewed != null) {
                response.setLeastReviewedSiteName(leastReviewed.getPlaceName());
                response.setLeastReviewedCount(leastReviewed.getReviewCount());
            }
        }

        // 5. Recent reviews (Top 5 APPROVED reviews only)
        response.setRecentFeedback(visitorFeedbackRepository.findTop5ByStatusOrderByCreatedAtDesc("APPROVED"));

        // 6. Popular Places (Top 5 by visits)
        response.setPopularPlaces(heritagePlaceRepository.findTop5ByOrderByVisitCountDesc());

        // 7. Review trends over time (group APPROVED reviews by date)
        List<VisitorFeedback> allApprovedFeedback = visitorFeedbackRepository.findByStatusOrderByCreatedAtDesc("APPROVED");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MMM dd");
        Map<String, Long> trends = new TreeMap<>();
        for (VisitorFeedback vf : allApprovedFeedback) {
            if (vf.getCreatedAt() != null) {
                String dateKey = vf.getCreatedAt().format(dtf);
                trends.put(dateKey, trends.getOrDefault(dateKey, 0L) + 1L);
            }
        }
        response.setReviewTrends(trends);

        return response;
    }
}
