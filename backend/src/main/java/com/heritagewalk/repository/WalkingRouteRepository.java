package com.heritagewalk.repository;

import com.heritagewalk.entity.WalkingRoute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WalkingRouteRepository extends JpaRepository<WalkingRoute, Long> {

    List<WalkingRoute> findByStatus(String status);
}
