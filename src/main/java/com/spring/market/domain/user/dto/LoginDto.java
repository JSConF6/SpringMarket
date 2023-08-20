package com.spring.market.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@ToString
public class LoginDto {
    private int id;
    private String login_id;
    private String member_password;
    private String nickname;
    private String path;

    private String roles = "ROLE_USER";

    public LoginDto(UserInfoDto userInfo) {
        this.id = userInfo.getId();
        this.login_id = userInfo.getUsername();
        this.member_password = userInfo.getPassword();
        this.nickname = userInfo.getNickname();
        this.path = userInfo.getPath();
    }

    @JsonIgnore
    public List<String> getRoleList(){
        if(this.roles.length()>0){
            return Arrays.asList(this.roles.split(","));
        }
        return new ArrayList<>();
    }
}
