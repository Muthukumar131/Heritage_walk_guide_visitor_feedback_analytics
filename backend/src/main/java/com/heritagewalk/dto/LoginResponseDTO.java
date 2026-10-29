package com.heritagewalk.dto;

public class LoginResponseDTO {

    private boolean authenticated;
    private Long userId;
    private String username;
    private String fullName;
    private String role;
    private String token;
    private String message;

    public LoginResponseDTO() {
    }

    public LoginResponseDTO(boolean authenticated, Long userId, String username, String fullName, String role, String token, String message) {
        this.authenticated = authenticated;
        this.userId = userId;
        this.username = username;
        this.fullName = fullName;
        this.role = role;
        this.token = token;
        this.message = message;
    }

    public LoginResponseDTO(boolean authenticated, String username, String fullName, String role, String token, String message) {
        this(authenticated, null, username, fullName, role, token, message);
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
