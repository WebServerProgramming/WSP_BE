package com.chawoomi.outbound.repository;

import com.chawoomi.outbound.entity.Review;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("SELECT r FROM Review r WHERE r.club.id = :clubId")
    List<Review> findByClubId(@Param("clubId") Long clubId);
}
