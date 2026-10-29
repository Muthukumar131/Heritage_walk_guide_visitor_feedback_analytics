package com.heritagewalk.controller;

import com.heritagewalk.dto.LoginRequestDTO;
import com.heritagewalk.dto.LoginResponseDTO;
import com.heritagewalk.dto.RegisterRequestDTO;
import com.heritagewalk.service.AdminAuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AdminAuthController {

    private final AdminAuthService adminAuthService;

    @Autowired
    public AdminAuthController(AdminAuthService adminAuthService) {
        this.adminAuthService = adminAuthService;
    }

    @PostMapping("/register")
    public ResponseEntity<LoginResponseDTO> register(@Valid @RequestBody RegisterRequestDTO request) {
        LoginResponseDTO response = adminAuthService.registerVisitor(request);
        if (response.isAuthenticated()) {
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO request) {
        LoginResponseDTO response = adminAuthService.authenticate(request);
        if (response.isAuthenticated()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }
}
