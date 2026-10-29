package com.heritagewalk.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "heritage_places")
public class HeritagePlace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String category;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;

    @Column(name = "historical_background", columnDefinition = "TEXT")
    private String historicalBackground;

    @Column(name = "historical_significance", columnDefinition = "TEXT")
    private String historicalSignificance;

    @Column(name = "year_or_period")
    private String yearOrPeriod;

    @Column(nullable = false)
    private String address;

    private Double latitude;
    private Double longitude;

    @Column(name = "image_url", length = 500)
    private String imageUrl;

    @Column(name = "opening_time")
    private String openingTime;

    @Column(name = "closing_time")
    private String closingTime;

    @Column(name = "entry_fee")
    private String entryFee;

    @Column(nullable = false)
    private String status = "ACTIVE";

    @Column(name = "visit_count")
    private Integer visitCount = 0;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public HeritagePlace() {
    }

    public HeritagePlace(Long id, String name, String category, String description, String historicalBackground,
                         String historicalSignificance, String yearOrPeriod, String address, Double latitude,
                         Double longitude, String imageUrl, String openingTime, String closingTime,
                         String entryFee, String status, Integer visitCount) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.description = description;
        this.historicalBackground = historicalBackground;
        this.historicalSignificance = historicalSignificance;
        this.yearOrPeriod = yearOrPeriod;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.imageUrl = imageUrl;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
        this.entryFee = entryFee;
        this.status = status;
        this.visitCount = visitCount;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHistoricalBackground() {
        return historicalBackground;
    }

    public void setHistoricalBackground(String historicalBackground) {
        this.historicalBackground = historicalBackground;
    }

    public String getHistoricalSignificance() {
        return historicalSignificance;
    }

    public void setHistoricalSignificance(String historicalSignificance) {
        this.historicalSignificance = historicalSignificance;
    }

    public String getYearOrPeriod() {
        return yearOrPeriod;
    }

    public void setYearOrPeriod(String yearOrPeriod) {
        this.yearOrPeriod = yearOrPeriod;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getOpeningTime() {
        return openingTime;
    }

    public void setOpeningTime(String openingTime) {
        this.openingTime = openingTime;
    }

    public String getClosingTime() {
        return closingTime;
    }

    public void setClosingTime(String closingTime) {
        this.closingTime = closingTime;
    }

    public String getEntryFee() {
        return entryFee;
    }

    public void setEntryFee(String entryFee) {
        this.entryFee = entryFee;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getVisitCount() {
        return visitCount;
    }

    public void setVisitCount(Integer visitCount) {
        this.visitCount = visitCount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
