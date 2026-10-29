package com.heritagewalk.repository;

import com.heritagewalk.entity.RouteStop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RouteStopRepository extends JpaRepository<RouteStop, Long> {

    List<RouteStop> findByRouteIdOrderBySequenceOrderAsc(Long routeId);

    void deleteByRouteId(Long routeId);
}
