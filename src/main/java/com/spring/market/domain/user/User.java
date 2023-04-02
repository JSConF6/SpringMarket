package com.spring.market.domain.user;

import lombok.Data;

@Data
public class User {
    private int id;
    private String username;
    private String password;
    private String nickname;
    private String withdraw;
    private String createAt;
    private String updateAt;
    private String withdrawAt;
}
