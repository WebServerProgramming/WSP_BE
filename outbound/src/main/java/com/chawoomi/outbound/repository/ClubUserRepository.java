package com.chawoomi.outbound.repository;

import com.chawoomi.outbound.entity.ClubUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClubUserRepository extends JpaRepository<ClubUser, Long> {

    List<ClubUser> findAllByClub_Id(Long id);

    List<ClubUser> findAllByUserId(Long userId);
}
