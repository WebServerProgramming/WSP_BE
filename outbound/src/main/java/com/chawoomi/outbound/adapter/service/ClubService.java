package com.chawoomi.outbound.adapter.service;

import com.chawoomi.outbound.adapter.service.dto.AllClubInfo;
import com.chawoomi.outbound.adapter.service.dto.ClubInfo;
import com.chawoomi.outbound.adapter.service.dto.ClubMember;
import com.chawoomi.outbound.entity.Club;
import com.chawoomi.outbound.entity.ClubUser;
import com.chawoomi.outbound.entity.Review;
import com.chawoomi.outbound.entity.User;
import com.chawoomi.outbound.repository.ClubRepository;
import com.chawoomi.outbound.repository.ClubUserRepository;
import com.chawoomi.outbound.repository.ReviewRepository;
import com.chawoomi.outbound.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClubService {

    private final ClubUserRepository clubUserRepository;
    private final ClubRepository clubRepository;
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;

    @Transactional(readOnly = true)
    public List<AllClubInfo> findAll() {
        // 모든 클럽 조회
        return clubRepository.findAll().stream()
                .map(club -> {
                    // 클럽별 리뷰 평균 평점 계산
                    Double averageRate = calculateAverageRate(club.getId());

                    // AllClubInfo 객체 생성
                    return new AllClubInfo(
                            club.getId(),
                            club.getClubName(),
                            averageRate != null ? Math.round(averageRate * 100.0) / 100.0 : null
                    );
                })
                .toList();
    }

    // 클럽별 평균 평점 계산
    private Double calculateAverageRate(Long clubId) {
        List<Review> reviews = reviewRepository.findByClubId(clubId);
        if (reviews.isEmpty()) {
            return null; // 리뷰가 없을 경우 null 반환
        }
        double totalRate = reviews.stream()
                .mapToInt(Review::getRating)
                .sum();
        return totalRate / reviews.size();
    }

    @Transactional(readOnly = true)
    public List<ClubInfo> findMyClub(Long userId) {

        // 1. userId를 기준으로 ClubUser 조회
        List<ClubUser> clubUsers = clubUserRepository.findAllByUserId(userId);

        // 2. ClubUser의 Club 정보를 ClubInfo로 매핑
        return clubUsers.stream()
                .map(clubUser -> new ClubInfo(
                        clubUser.getClub().getId(),
                        clubUser.getClub().getClubName()
                ))
                .toList();
    }

    @Transactional
    public void joinClub(Long userId, Long clubId) {
        // 1. 클럽 및 사용자 조회
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new IllegalArgumentException("해당 클럽이 존재하지 않습니다. clubId: " + clubId));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다. userId: " + userId));

        // 2. 중복 가입 여부 확인
        boolean isAlreadyMember = clubUserRepository.existsByUserIdAndClubId(userId, clubId);
        if (isAlreadyMember) {
            throw new IllegalStateException("사용자는 이미 클럽에 가입되어 있습니다. clubId: " + clubId + ", userId: " + userId);
        }

        // 3. ClubUser 엔티티 생성 및 저장
        ClubUser clubUser = new ClubUser();
        clubUser.assignClubAndUser(club, user);
        clubUser.assignRole(1L);

        clubUserRepository.save(clubUser);
    }

    public List<ClubMember> findMembers(Long id) {
        // 1. 동아리 사용자 조회
        final List<ClubUser> clubUsers = clubUserRepository.findAllByClub_Id(id);

        // 2. ClubUser에서 모든 UserId 추출
        List<Long> userIds = clubUsers.stream()
                .map(clubUser -> clubUser.getUser().getId())
                .toList();

        // 3. UserId 리스트를 사용해 User 엔티티 조회
        return userRepository.findAllByIdIn(userIds).stream()
                .map(user -> new ClubMember(user.getId(), user.getName()))
                .toList();
    }

}
