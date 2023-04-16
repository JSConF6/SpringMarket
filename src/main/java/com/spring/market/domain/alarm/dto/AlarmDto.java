package com.spring.market.domain.alarm.dto;

import lombok.Data;

@Data
public class AlarmDto {
    private int roomId;
    private int sellerId;
    private String message;
}
