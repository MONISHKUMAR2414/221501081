package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.ShortUrl;
import com.example.demo.repository.ShortUrlRepository;

@Service
public class UrlShortenerService {
    @Autowired
    private ShortUrlRepository urlRepo;

    public ShortUrl createShortUrl(String longUrl, Integer validity, String customCode) {
        String shortcode = (customCode != null) ? customCode : UUID.randomUUID().toString().substring(0, 6);
        if (urlRepo.existsById(shortcode)) throw new RuntimeException("Shortcode collision");

        LocalDateTime expiry = LocalDateTime.now().plusMinutes(validity != null ? validity : 30);
        ShortUrl shortUrl = new ShortUrl(shortcode, longUrl, LocalDateTime.now(), expiry, 0);
        return urlRepo.save(shortUrl);
    }
}