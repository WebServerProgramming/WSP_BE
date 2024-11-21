package com.chawoomi.outbound.repository;

import com.chawoomi.outbound.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
