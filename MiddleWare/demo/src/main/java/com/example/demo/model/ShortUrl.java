package com.example.demo.model;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
@Entity
public class ShortUrl {
    @Id
    private String shortcode;

    @Column(nullable = false)
    private String longUrl;

    private LocalDateTime createdAt;
    private LocalDateTime expiry;
    private int clickCount;

    public ShortUrl() {}

    public ShortUrl(String shortcode, String longUrl, LocalDateTime createdAt, LocalDateTime expiry, int clickCount) {
        this.shortcode = shortcode;
        this.longUrl = longUrl;
        this.createdAt = createdAt;
        this.expiry = expiry;
        this.clickCount = clickCount;
    }

    // 🛠 Getters
    public String getShortcode() { return shortcode; }
    public String getLongUrl() { return longUrl; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getExpiry() { return expiry; }
    public int getClickCount() { return clickCount; }

    // 🛠 Setters
    public void setShortcode(String shortcode) { this.shortcode = shortcode; }
    public void setLongUrl(String longUrl) { this.longUrl = longUrl; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public void setExpiry(LocalDateTime expiry) { this.expiry = expiry; }
    public void setClickCount(int clickCount) { this.clickCount = clickCount; }
}