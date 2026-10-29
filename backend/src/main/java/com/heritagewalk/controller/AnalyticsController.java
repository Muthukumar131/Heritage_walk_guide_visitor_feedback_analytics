package com.heritagewalk.controller;

import com.heritagewalk.dto.AnalyticsResponseDTO;
import com.heritagewalk.service.AnalyticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/analytics")
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    @Autowired
    public AnalyticsController(AnalyticsService analyticsService) {
        this.analyticsService = analyticsService;
    }

    @GetMapping("/overview")
    public ResponseEntity<AnalyticsResponseDTO> getOverview() {
        AnalyticsResponseDTO overview = analyticsService.getAnalyticsOverview();
        return ResponseEntity.ok(overview);
    }
}
