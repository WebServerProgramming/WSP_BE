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
                .map(club -> new ClubInfo(club.getClub_id(), club.getClubName()))
                .toList();
    }

    public List<ClubMember> findMembers(Long id) {

        final ClubUser clubUsers = clubUserRepository.findAllByClub_Club_id(id);
        return userRepository.findAllByUserId(clubUsers.getUser().getUserId()).stream()
                .map(user -> new ClubMember(user.getUserId(), user.getName()))
                .toList();
    }
}
