package com.chawoomi.outbound.repository;

import com.chawoomi.outbound.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findBySubId(String subId);

    Optional<User> findByEmail(String email);

    List<User> findAllByUserId(Long id);
}
