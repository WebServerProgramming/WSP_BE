package com.chawoomi.outbound.adapter.service;

import com.chawoomi.outbound.adapter.service.dto.ReviewSummaryDto;
import com.chawoomi.outbound.entity.Club;
import com.chawoomi.outbound.entity.Review;
import com.chawoomi.outbound.entity.User;
import com.chawoomi.outbound.repository.ClubRepository;
import com.chawoomi.outbound.repository.ReviewRepository;
import com.chawoomi.outbound.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ClubRepository clubRepository;
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;

    @Transactional(readOnly = true)
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

    @Transactional
    public void saveReview(Long userId, Long clubId, Integer rating, String content) {
        // 1. 클럽 및 사용자 조회
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new IllegalArgumentException("해당 클럽이 존재하지 않습니다. clubId: " + clubId));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다. userId: " + userId));

        // 2. 중복 작성 여부 확인
        boolean isAlreadyWritten = reviewRepository.existsByUserIdAndClubId(userId, clubId);
        if (isAlreadyWritten) {
            throw new IllegalStateException("사용자는 이미 리뷰를 작성했습니다. clubId: " + clubId + ", userId: " + userId);
        }

        // 3. Review 엔티티 생성 및 저장
        Review review = new Review(rating, content);
        review.assignClubAndUser(club, user);

        reviewRepository.save(review);
    }
}
