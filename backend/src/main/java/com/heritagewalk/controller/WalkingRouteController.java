package com.heritagewalk.controller;

import com.heritagewalk.dto.RouteStopDTO;
import com.heritagewalk.entity.RouteStop;
import com.heritagewalk.entity.WalkingRoute;
import com.heritagewalk.service.WalkingRouteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/routes")
public class WalkingRouteController {

    private final WalkingRouteService walkingRouteService;

    @Autowired
    public WalkingRouteController(WalkingRouteService walkingRouteService) {
        this.walkingRouteService = walkingRouteService;
    }

    // 1. Get all walking routes
    @GetMapping
    public ResponseEntity<List<WalkingRoute>> getAllRoutes() {
        return ResponseEntity.ok(walkingRouteService.getAllRoutes());
    }

    // 2. Get single walking route by ID
    @GetMapping("/{id}")
    public ResponseEntity<WalkingRoute> getRouteById(@PathVariable Long id) {
        return ResponseEntity.ok(walkingRouteService.getRouteById(id));
    }

    // 3. Create new route (Admin)
    @PostMapping
    public ResponseEntity<WalkingRoute> createRoute(@Valid @RequestBody WalkingRoute route) {
        WalkingRoute created = walkingRouteService.createRoute(route);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    // 4. Update route details (Admin)
    @PutMapping("/{id}")
    public ResponseEntity<WalkingRoute> updateRoute(@PathVariable Long id, @Valid @RequestBody WalkingRoute route) {
        WalkingRoute updated = walkingRouteService.updateRoute(id, route);
        return ResponseEntity.ok(updated);
    }

    // 5. Delete route (Admin)
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteRoute(@PathVariable Long id) {
        walkingRouteService.deleteRoute(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Walking route with ID " + id + " was deleted successfully.");
        return ResponseEntity.ok(response);
    }

    // 6. Get ordered stops for route
    @GetMapping("/{id}/stops")
    public ResponseEntity<List<RouteStop>> getRouteStops(@PathVariable Long id) {
        return ResponseEntity.ok(walkingRouteService.getStopsForRoute(id));
    }

    // 7. Add stop to route (Admin)
    @PostMapping("/{id}/stops")
    public ResponseEntity<RouteStop> addStopToRoute(
            @PathVariable Long id,
            @Valid @RequestBody RouteStopDTO dto) {
        RouteStop stop = walkingRouteService.addStopToRoute(id, dto);
        return new ResponseEntity<>(stop, HttpStatus.CREATED);
    }

    // 8. Remove stop from route (Admin)
    @DeleteMapping("/stops/{stopId}")
    public ResponseEntity<Map<String, String>> removeStopFromRoute(@PathVariable Long stopId) {
        walkingRouteService.removeStopFromRoute(stopId);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Route stop with ID " + stopId + " was removed successfully.");
        return ResponseEntity.ok(response);
    }
}
