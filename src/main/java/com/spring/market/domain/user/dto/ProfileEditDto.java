package com.spring.market.domain.user.dto;

import lombok.Data;

@Data
public class ProfileEditDto {
    private int id;
    private String username;
    private String nickname;
    private String path;

}
