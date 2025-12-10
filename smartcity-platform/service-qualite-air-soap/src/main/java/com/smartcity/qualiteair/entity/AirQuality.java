package com.smartcity.qualiteair.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "air_quality")
public class AirQuality {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String quartier;
    
    @Column(nullable = false)
    private Integer aqi;
    
    private String polluants;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    public AirQuality() {
        this.updatedAt = LocalDateTime.now();
    }
    
    public AirQuality(String quartier, Integer aqi, String polluants) {
        this.quartier = quartier;
        this.aqi = aqi;
        this.polluants = polluants;
        this.updatedAt = LocalDateTime.now();
    }
    
    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getQuartier() { return quartier; }
    public void setQuartier(String quartier) { this.quartier = quartier; }
    
    public Integer getAqi() { return aqi; }
    public void setAqi(Integer aqi) { 
        this.aqi = aqi;
        this.updatedAt = LocalDateTime.now();
    }
    
    public String getPolluants() { return polluants; }
    public void setPolluants(String polluants) { 
        this.polluants = polluants;
        this.updatedAt = LocalDateTime.now();
    }
    
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}