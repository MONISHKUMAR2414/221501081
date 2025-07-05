package com.example.demo.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.model.ClickStats;

public interface ClickStatsRepository extends JpaRepository<ClickStats, Long> {
    List<ClickStats> findByShortcode(String shortcode);
}