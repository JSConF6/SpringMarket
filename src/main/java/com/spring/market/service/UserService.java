package com.spring.market.service;

import com.spring.market.domain.user.User;
import com.spring.market.domain.user.UserMapper;
import com.spring.market.domain.user.dto.LoginDto;
import com.spring.market.domain.user.dto.PasswordChangeDto;
import com.spring.market.domain.user.dto.SignInDto;
import com.spring.market.domain.user.dto.UserInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;

    private final BCryptPasswordEncoder passwordEncoder;

    public void join(SignInDto signInDto){
        userMapper.join(signInDto);
    }
    public String findEmail(SignInDto signInDto){
        int count = userMapper.findMember(signInDto);
        if(count > 0){
            return "001";
        }else {
            return "000";
        }
    }

    @Transactional
    public String passwordUpdate(LoginDto loginDto, PasswordChangeDto passwordChangeDto) {
        User updateUser = userMapper.findByUsername(loginDto.getLogin_id()).orElse(null);

        if(updateUser == null) {
            return null;
        }

        if (!passwordEncoder.matches(passwordChangeDto.getCurrentPassword(), updateUser.getPassword())) {
            return null;
        }

        String encPassword = passwordEncoder.encode(passwordChangeDto.getChangePassword());
        updateUser.setPassword(encPassword);
        userMapper.updateById(updateUser);

        return "SUCCESS";
    }

    @Transactional
    public String withdraw(LoginDto loginDto, String currentPassword) {

        User withdrawUser = userMapper.findByUsername(loginDto.getLogin_id()).orElse(null);

        if (withdrawUser == null) {
            return null;
        }

        if (!passwordEncoder.matches(currentPassword, withdrawUser.getPassword())) {
            return null;
        }

        userMapper.withdraw(loginDto.getId());

        return "SUCCESS";
    }

    public UserInfoDto findById(String login_id) {

        return userMapper.findById(login_id);
    }
}
