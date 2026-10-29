package com.heritagewalk.service;

import com.heritagewalk.dto.LoginRequestDTO;
import com.heritagewalk.dto.LoginResponseDTO;
import com.heritagewalk.dto.RegisterRequestDTO;
import com.heritagewalk.entity.AdminUser;
import com.heritagewalk.entity.User;
import com.heritagewalk.repository.AdminUserRepository;
import com.heritagewalk.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AdminAuthService {

    private final AdminUserRepository adminUserRepository;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public AdminAuthService(AdminUserRepository adminUserRepository, UserRepository userRepository) {
        this.adminUserRepository = adminUserRepository;
        this.userRepository = userRepository;
    }

    public LoginResponseDTO registerVisitor(RegisterRequestDTO request) {
        String username = request.getUsername().trim();
        String email = request.getEmail().trim().toLowerCase();

        if (userRepository.existsByUsername(username)) {
            return new LoginResponseDTO(false, null, null, null, null, null, "Username is already taken.");
        }
        if (userRepository.existsByEmail(email)) {
            return new LoginResponseDTO(false, null, null, null, null, null, "Email address is already registered.");
        }

        User visitor = new User();
        visitor.setUsername(username);
        visitor.setPassword(passwordEncoder.encode(request.getPassword()));
        visitor.setFullName(request.getFullName().trim());
        visitor.setEmail(email);
        visitor.setRole("ROLE_VISITOR");

        User saved = userRepository.save(visitor);
        String token = "hw-token-" + UUID.randomUUID().toString();

        return new LoginResponseDTO(
                true,
                saved.getId(),
                saved.getUsername(),
                saved.getFullName(),
                saved.getRole(),
                token,
                "Visitor account registered successfully."
        );
    }

    public LoginResponseDTO authenticate(LoginRequestDTO request) {
        String username = request.getUsername().trim();

        // 1. Check in primary Users table (both Visitors and Admins)
        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                String token = "hw-token-" + UUID.randomUUID().toString();
                return new LoginResponseDTO(
                        true,
                        user.getId(),
                        user.getUsername(),
                        user.getFullName(),
                        user.getRole(),
                        token,
                        "Authentication successful."
                );
            }
        }

        // 2. Fallback check in AdminUsers table
        Optional<AdminUser> adminOpt = adminUserRepository.findByUsername(username);
        if (adminOpt.isPresent()) {
            AdminUser admin = adminOpt.get();
            if (passwordEncoder.matches(request.getPassword(), admin.getPassword())) {
                String token = "hw-token-" + UUID.randomUUID().toString();
                return new LoginResponseDTO(
                        true,
                        admin.getId(),
                        admin.getUsername(),
                        admin.getFullName(),
                        admin.getRole(),
                        token,
                        "Authentication successful."
                );
            }
        }

        return new LoginResponseDTO(
                false,
                null,
                null,
                null,
                null,
                null,
                "Invalid username or password."
        );
    }
}
