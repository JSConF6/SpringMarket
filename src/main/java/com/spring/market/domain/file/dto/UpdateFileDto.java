package com.spring.market.domain.file.dto;

import lombok.Data;

@Data
public class UpdateFileDto {
    private int id;
    private String originalName;
    private String name;
    private String path;
}
