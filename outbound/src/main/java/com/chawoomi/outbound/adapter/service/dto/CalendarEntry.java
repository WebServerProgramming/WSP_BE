package com.chawoomi.outbound.adapter.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CalendarEntry {
    private String date;    // yyyy-MM-dd 형식의 날짜
    private String dateType; // "Start" 또는 "End"
}
