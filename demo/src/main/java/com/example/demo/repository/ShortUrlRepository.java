package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.model.ShortUrl;

public interface ShortUrlRepository extends JpaRepository<ShortUrl, String> {}