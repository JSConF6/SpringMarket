package com.spring.market.domain.user.dto;

import lombok.Data;

@Data
public class PasswordChangeDto {

    private String currentPassword;
    private String changePassword;
}
