package com.spring.market.domain.user.dto;

import lombok.Data;

@Data
public class SignInDto {
    private int id;
    private String username;
    private String password;
    private String nickname;
    private String phone_number;
}
