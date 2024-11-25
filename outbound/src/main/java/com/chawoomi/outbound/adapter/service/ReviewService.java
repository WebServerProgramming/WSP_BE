package com.chawoomi.outbound.adapter.service;

import com.chawoomi.outbound.adapter.service.dto.ReviewSummaryDto;
import com.chawoomi.outbound.entity.Review;
import com.chawoomi.outbound.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public ReviewSummaryDto findAll(Long clubId) {
        // 1. 클럽의 리뷰 조회
        List<Review> reviews = reviewRepository.findByClubId(clubId);

        // 2. 총합 계산
        double totalRate = reviews.stream()
                .mapToInt(Review::getRating)
                .sum();
        totalRate = totalRate / reviews.size();

        // 3. 리뷰 리스트 생성
        List<ReviewSummaryDto.ReviewDetailDto> reviewDetails = reviews.stream()
                .map(review -> new ReviewSummaryDto.ReviewDetailDto(
                        review.getContent(),
                        review.getRating()
                ))
                .toList();

        // 4. DTO 생성 및 반환
        return new ReviewSummaryDto(totalRate, reviewDetails);
    }
}
