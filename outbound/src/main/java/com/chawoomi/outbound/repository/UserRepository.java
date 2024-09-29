package com.chawoomi.outbound.repository;

import com.chawoomi.outbound.entity.JpaUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<JpaUser, Long> {

    Optional<JpaUser> findByUserSubId(String subId);
}
