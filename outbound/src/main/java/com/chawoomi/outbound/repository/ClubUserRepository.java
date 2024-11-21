package com.chawoomi.outbound.repository;

import com.chawoomi.outbound.entity.ClubUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClubUserRepository extends JpaRepository<ClubUser, Long> {

    ClubUser findAllByClub_Club_id(Long id);
}
