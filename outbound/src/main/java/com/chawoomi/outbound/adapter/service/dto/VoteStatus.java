package com.chawoomi.outbound.adapter.service.dto;

import com.chawoomi.outbound.entity.Vote;
import com.chawoomi.outbound.entity.VoteItem;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class VoteStatus {

    private Vote vote;
    private List<VoteItem> items;
}