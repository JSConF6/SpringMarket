package com.spring.market.domain.user.dto;

import lombok.Data;

@Data
public class UserInfoDto {
    private int id;
    private String username;
    private String password;
    private String nickname;
    private String phone_number;
    private String withdraw;
    private String create_at;
    private String update_at;
    private String withdraw_at;

}
