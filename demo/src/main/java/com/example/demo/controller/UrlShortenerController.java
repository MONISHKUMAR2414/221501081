package com.example.demo.controller;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.*;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.model.ShortUrl;
import com.example.demo.model.ClickStats;
import com.example.demo.repository.ShortUrlRepository;
import com.example.demo.repository.ClickStatsRepository;
import com.example.demo.service.UrlShortenerService;

@RestController
@RequestMapping("/")
public class UrlShortenerController {

    @Autowired
    private ShortUrlRepository shortUrlRepository;

    @Autowired
    private ClickStatsRepository clickStatsRepository;

    @Autowired
    private UrlShortenerService service;

    @PostMapping("/shorturls")
    public ResponseEntity<?> create(@RequestBody Map<String, Object> request) {
        String url = request.get("url").toString();
        Integer validity = request.containsKey("validity") ? (Integer) request.get("validity") : null;
        String shortcode = request.containsKey("shortcode") ? request.get("shortcode").toString() : null;

        ShortUrl shortUrl = service.createShortUrl(url, validity, shortcode);

        Map<String, Object> response = new HashMap<>();
        response.put("shortLink", "http://localhost:8080/" + shortUrl.getShortcode());
        response.put("expiry", shortUrl.getExpiry().toString());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{shortcode}")
    public ResponseEntity<Void> redirect(@PathVariable String shortcode, HttpServletRequest request) {
        ShortUrl url = shortUrlRepository.findById(shortcode)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid shortcode"));

        if (url.getExpiry().isBefore(LocalDateTime.now())) {
            throw new ResponseStatusException(HttpStatus.GONE, "Link expired");
        }

        url.setClickCount(url.getClickCount() + 1);
        shortUrlRepository.save(url);

        ClickStats stats = new ClickStats();
        stats.setShortcode(shortcode);
        stats.setClickedAt(LocalDateTime.now());
        stats.setReferrer(request.getHeader("referer"));
        stats.setGeoLocation(request.getRemoteAddr());
        clickStatsRepository.save(stats);

        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(url.getLongUrl())).build();
    }

    @GetMapping("/shorturls/{shortcode}")
    public ResponseEntity<?> getStats(@PathVariable String shortcode) {
        ShortUrl url = shortUrlRepository.findById(shortcode)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Shortcode not found"));

        List<ClickStats> clicks = clickStatsRepository.findByShortcode(shortcode);

        Map<String, Object> response = new HashMap<>();
        response.put("shortcode", shortcode);
        response.put("longUrl", url.getLongUrl());
        response.put("createdAt", url.getCreatedAt());
        response.put("expiry", url.getExpiry());
        response.put("totalClicks", url.getClickCount());
        response.put("clickDetails", clicks.stream().map(click -> Map.of(
            "timestamp", click.getClickedAt(),
            "referrer", click.getReferrer(),
            "geoLocation", click.getGeoLocation()
        )).toList());

        return ResponseEntity.ok(response);
    }
}