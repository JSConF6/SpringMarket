package com.spring.market.domain.alarm;

import lombok.Data;

@Data
public class Alarm {
    private int id;
    private int userId;
    private String type;
    private String url;
    private String message;
    private String readYn;
    private String createAt;
}
