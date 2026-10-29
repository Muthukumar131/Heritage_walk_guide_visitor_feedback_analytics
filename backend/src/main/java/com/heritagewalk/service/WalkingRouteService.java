package com.heritagewalk.service;

import com.heritagewalk.dto.RouteStopDTO;
import com.heritagewalk.entity.HeritagePlace;
import com.heritagewalk.entity.RouteStop;
import com.heritagewalk.entity.WalkingRoute;
import com.heritagewalk.exception.ResourceNotFoundException;
import com.heritagewalk.repository.HeritagePlaceRepository;
import com.heritagewalk.repository.RouteStopRepository;
import com.heritagewalk.repository.WalkingRouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class WalkingRouteService {

    private final WalkingRouteRepository walkingRouteRepository;
    private final RouteStopRepository routeStopRepository;
    private final HeritagePlaceRepository heritagePlaceRepository;

    @Autowired
    public WalkingRouteService(WalkingRouteRepository walkingRouteRepository,
                               RouteStopRepository routeStopRepository,
                               HeritagePlaceRepository heritagePlaceRepository) {
        this.walkingRouteRepository = walkingRouteRepository;
        this.routeStopRepository = routeStopRepository;
        this.heritagePlaceRepository = heritagePlaceRepository;
    }

    public List<WalkingRoute> getAllRoutes() {
        return walkingRouteRepository.findAll();
    }

    public WalkingRoute getRouteById(Long id) {
        return walkingRouteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Walking route not found with ID: " + id));
    }

    public WalkingRoute createRoute(WalkingRoute route) {
        if (route.getStatus() == null || route.getStatus().trim().isEmpty()) {
            route.setStatus("ACTIVE");
        }
        return walkingRouteRepository.save(route);
    }

    public WalkingRoute updateRoute(Long id, WalkingRoute updatedRoute) {
        WalkingRoute existing = getRouteById(id);

        existing.setName(updatedRoute.getName());
        existing.setDescription(updatedRoute.getDescription());
        existing.setDistanceKm(updatedRoute.getDistanceKm());
        existing.setEstimatedDuration(updatedRoute.getEstimatedDuration());
        existing.setDifficultyLevel(updatedRoute.getDifficultyLevel());
        existing.setStartingPoint(updatedRoute.getStartingPoint());
        existing.setEndingPoint(updatedRoute.getEndingPoint());
        existing.setImageUrl(updatedRoute.getImageUrl());
        if (updatedRoute.getStatus() != null) {
            existing.setStatus(updatedRoute.getStatus());
        }

        return walkingRouteRepository.save(existing);
    }

    public void deleteRoute(Long id) {
        WalkingRoute route = getRouteById(id);
        walkingRouteRepository.delete(route);
    }

    public List<RouteStop> getStopsForRoute(Long routeId) {
        // Validate route exists
        getRouteById(routeId);
        return routeStopRepository.findByRouteIdOrderBySequenceOrderAsc(routeId);
    }

    @Transactional
    public RouteStop addStopToRoute(Long routeId, RouteStopDTO dto) {
        WalkingRoute route = getRouteById(routeId);
        HeritagePlace place = heritagePlaceRepository.findById(dto.getHeritagePlaceId())
                .orElseThrow(() -> new ResourceNotFoundException("Heritage place not found with ID: " + dto.getHeritagePlaceId()));

        RouteStop stop = new RouteStop();
        stop.setRoute(route);
        stop.setHeritagePlace(place);
        stop.setSequenceOrder(dto.getSequenceOrder());
        stop.setStopNotes(dto.getStopNotes());

        return routeStopRepository.save(stop);
    }

    @Transactional
    public void removeStopFromRoute(Long stopId) {
        RouteStop stop = routeStopRepository.findById(stopId)
                .orElseThrow(() -> new ResourceNotFoundException("Route stop not found with ID: " + stopId));
        routeStopRepository.delete(stop);
    }
}
