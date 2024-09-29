package com.chawoomi.outbound.repository;

import com.chawoomi.outbound.entity.JpaPhoto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoRepository extends JpaRepository<JpaPhoto, Long> {

}
