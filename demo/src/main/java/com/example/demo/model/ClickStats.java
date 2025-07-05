package com.example.demo.model;
import java.time.LocalDateTime;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class ClickStats {
    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String shortcode;
    private LocalDateTime clickedAt;
    private String referrer;
    private String geoLocation;

    public ClickStats() {}

    // 🛠 Getters
    public Long getId() { return id; }
    public String getShortcode() { return shortcode; }
    public LocalDateTime getClickedAt() { return clickedAt; }
    public String getReferrer() { return referrer; }
    public String getGeoLocation() { return geoLocation; }

    // 🛠 Setters
    public void setShortcode(String shortcode) { this.shortcode = shortcode; }
    public void setClickedAt(LocalDateTime clickedAt) { this.clickedAt = clickedAt; }
    public void setReferrer(String referrer) { this.referrer = referrer; }
    public void setGeoLocation(String geoLocation) { this.geoLocation = geoLocation; }
}