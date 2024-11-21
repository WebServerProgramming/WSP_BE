package com.chawoomi.outbound.adapter.service;

import com.chawoomi.outbound.adapter.service.dto.ClubInfo;
import com.chawoomi.outbound.adapter.service.dto.ClubMember;
import com.chawoomi.outbound.entity.ClubUser;
import com.chawoomi.outbound.repository.ClubRepository;
import com.chawoomi.outbound.repository.ClubUserRepository;
import com.chawoomi.outbound.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClubService {

    private final ClubUserRepository clubUserRepository;
    private final ClubRepository clubRepository;
    private final UserRepository userRepository;

    public List<ClubInfo> findAll() {

        return clubRepository.findAll().stream()
                .map(club -> new ClubInfo(club.getId(), club.getClubName()))
                .toList();
    }

    public List<ClubMember> findMembers(Long id) {
        // 1. 동아리 사용자 조회
        final List<ClubUser> clubUsers = clubUserRepository.findAllByClub_Id(id);

        // 2. ClubUser에서 모든 UserId 추출
        List<Long> userIds = clubUsers.stream()
                .map(clubUser -> clubUser.getUser().getId())
                .toList();

        // 3. UserId 리스트를 사용해 User 엔티티 조회
        return userRepository.findAllByUserIdIn(userIds).stream()
                .map(user -> new ClubMember(user.getId(), user.getName()))
                .toList();
    }

}
