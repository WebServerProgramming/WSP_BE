package com.chawoomi.outbound.entity;

import com.chawoomi.core.exception.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "Schedule")
public class Schedule extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_id")
    private Long scheduleId;

    @Column(name = "title", nullable = false)
    private String title;


    @Column(name = "event_type", nullable = false)
    private String eventType;

    @Column(name = "location", nullable = true)
    private String location;

    @Column(name = "start_date", nullable = true)
    private LocalDateTime start_date;
    
    @Column(name = "end_date", nullable = true)
    private LocalDateTime end_date;



}