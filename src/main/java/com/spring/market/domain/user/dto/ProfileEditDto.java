package com.spring.market.domain.user.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ProfileEditDto {
    private int id;
    private String username;
    private String nickname;
    private String path;
    private String originalName;
    private String name;
    private MultipartFile file;
}
