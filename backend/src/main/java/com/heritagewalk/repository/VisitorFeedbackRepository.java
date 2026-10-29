package com.heritagewalk.repository;

import com.heritagewalk.entity.VisitorFeedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface VisitorFeedbackRepository extends JpaRepository<VisitorFeedback, Long> {

    // Public view: Approved reviews for a place
    List<VisitorFeedback> findByHeritagePlaceIdAndStatusOrderByCreatedAtDesc(Long heritagePlaceId, String status);

    // Legacy fallback: All reviews for a place
    List<VisitorFeedback> findByHeritagePlaceIdOrderByCreatedAtDesc(Long heritagePlaceId);

    // Visitor personal reviews (all statuses for this user)
    List<VisitorFeedback> findByUserIdOrderByCreatedAtDesc(Long userId);

    List<VisitorFeedback> findByVisitorEmailOrderByCreatedAtDesc(String visitorEmail);

    Long countByUserId(Long userId);

    @Query("SELECT AVG(f.rating) FROM VisitorFeedback f WHERE f.userId = :userId")
    Double calculateAverageRatingByUser(@Param("userId") Long userId);

    // Public lists: Approved only
    List<VisitorFeedback> findByStatusOrderByCreatedAtDesc(String status);

    List<VisitorFeedback> findTop5ByStatusOrderByCreatedAtDesc(String status);

    List<VisitorFeedback> findAllByOrderByCreatedAtDesc();

    List<VisitorFeedback> findTop5ByOrderByCreatedAtDesc();

    // Counts by status
    long countByStatus(String status);

    @Query("SELECT COUNT(f) FROM VisitorFeedback f WHERE f.rating = :rating AND f.status = 'APPROVED'")
    Long countApprovedByRating(@Param("rating") Integer rating);

    Long countByRating(Integer rating);

    // Overall average rating: APPROVED only
    @Query("SELECT AVG(f.rating) FROM VisitorFeedback f WHERE f.status = 'APPROVED'")
    Double calculateOverallAverageRating();

    // Place average rating: APPROVED only
    @Query("SELECT AVG(f.rating) FROM VisitorFeedback f WHERE f.heritagePlace.id = :placeId AND f.status = 'APPROVED'")
    Double calculateAverageRatingForPlace(@Param("placeId") Long placeId);

    // Per-place rating aggregates: APPROVED only
    @Query("SELECT f.heritagePlace.id, f.heritagePlace.name, AVG(f.rating), COUNT(f) " +
           "FROM VisitorFeedback f " +
           "WHERE f.status = 'APPROVED' " +
           "GROUP BY f.heritagePlace.id, f.heritagePlace.name")
    List<Object[]> getPlaceRatingAggregates();

    // Admin filter with optional status (PENDING, APPROVED, REJECTED, or ALL)
    @Query("SELECT f FROM VisitorFeedback f WHERE " +
           "(:status IS NULL OR :status = '' OR :status = 'ALL' OR f.status = :status) AND " +
           "(:placeId IS NULL OR f.heritagePlace.id = :placeId) AND " +
           "(:minRating IS NULL OR f.rating >= :minRating) AND " +
           "(:startDate IS NULL OR f.visitDate >= :startDate) AND " +
           "(:endDate IS NULL OR f.visitDate <= :endDate) AND " +
           "(:search IS NULL OR LOWER(f.comment) LIKE LOWER(CONCAT('%', :search, '%')) OR LOWER(f.visitorName) LIKE LOWER(CONCAT('%', :search, '%'))) " +
           "ORDER BY f.createdAt DESC")
    List<VisitorFeedback> findFilteredReviewsForAdmin(
            @Param("status") String status,
            @Param("placeId") Long placeId,
            @Param("minRating") Integer minRating,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("search") String search);

    // Approved filter for NGO reports
    @Query("SELECT f FROM VisitorFeedback f WHERE " +
           "f.status = 'APPROVED' AND " +
           "(:placeId IS NULL OR f.heritagePlace.id = :placeId) AND " +
           "(:minRating IS NULL OR f.rating >= :minRating) AND " +
           "(:startDate IS NULL OR f.visitDate >= :startDate) AND " +
           "(:endDate IS NULL OR f.visitDate <= :endDate) AND " +
           "(:search IS NULL OR LOWER(f.comment) LIKE LOWER(CONCAT('%', :search, '%')) OR LOWER(f.visitorName) LIKE LOWER(CONCAT('%', :search, '%'))) " +
           "ORDER BY f.createdAt DESC")
    List<VisitorFeedback> findFilteredApprovedReviews(
            @Param("placeId") Long placeId,
            @Param("minRating") Integer minRating,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("search") String search);

    // Backward-compatible method
    default List<VisitorFeedback> findFilteredReviews(Long placeId, Integer minRating, LocalDate startDate, LocalDate endDate, String search) {
        return findFilteredReviewsForAdmin(null, placeId, minRating, startDate, endDate, search);
    }
}
