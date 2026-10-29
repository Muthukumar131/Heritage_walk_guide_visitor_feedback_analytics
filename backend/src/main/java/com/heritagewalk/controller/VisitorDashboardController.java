package com.heritagewalk.controller;

import com.heritagewalk.dto.VisitorDashboardDTO;
import com.heritagewalk.service.VisitorDashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/visitor")
public class VisitorDashboardController {

    private final VisitorDashboardService visitorDashboardService;

    @Autowired
    public VisitorDashboardController(VisitorDashboardService visitorDashboardService) {
        this.visitorDashboardService = visitorDashboardService;
    }

    @GetMapping("/dashboard")
    public ResponseEntity<VisitorDashboardDTO> getVisitorDashboard(
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) String email) {
        VisitorDashboardDTO dto = visitorDashboardService.getVisitorDashboard(userId, email);
        return ResponseEntity.ok(dto);
    }
}
