package com.spring.market.service;

import com.spring.market.domain.user.User;
import com.spring.market.domain.user.UserMapper;
import com.spring.market.domain.user.dto.PasswordChangeDto;
import com.spring.market.domain.user.dto.SignInDto;
import com.spring.market.domain.user.dto.UserInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;

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
    public String passwordUpdate(User user, PasswordChangeDto passwordChangeDto) {
        user.setPassword(passwordChangeDto.getCurrentPassword());
        User updateUser = userMapper.findByUsernameAndPassword(user).orElse(null);

        if(updateUser == null) {
            return null;
        }

        updateUser.setPassword(passwordChangeDto.getChangePassword());
        userMapper.updateById(updateUser);

        return "SUCCESS";
    }

    @Transactional
    public String withdraw(User user, String currentPassword) {
        user.setPassword(currentPassword);
        User withdrawUser = userMapper.findByUsernameAndPassword(user).orElse(null);

        if (withdrawUser == null) {
            return null;
        }

        userMapper.withdraw(user);

        return "SUCCESS";
    }

    public UserInfoDto findById(String login_id) {

        return userMapper.findById(login_id);
    }
}
