package com.spring.market.domain.file.dto;

import lombok.Data;

@Data
public class SaveFileDto {
    private int userId;
    private String originalName;
    private String name;
    private String path;
    private String type;
}
