package com.heritagewalk.repository;

import com.heritagewalk.entity.HeritagePlace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface HeritagePlaceRepository extends JpaRepository<HeritagePlace, Long> {

    List<HeritagePlace> findByStatus(String status);

    List<HeritagePlace> findByCategoryIgnoreCase(String category);

    @Query("SELECT p FROM HeritagePlace p WHERE " +
           "LOWER(p.name) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(p.category) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(p.address) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(p.description) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<HeritagePlace> searchPlaces(@Param("query") String query);

    @Transactional
    @Modifying
    @Query("UPDATE HeritagePlace p SET p.visitCount = COALESCE(p.visitCount, 0) + 1 WHERE p.id = :id")
    void incrementVisitCount(@Param("id") Long id);

    List<HeritagePlace> findTop5ByOrderByVisitCountDesc();
}
