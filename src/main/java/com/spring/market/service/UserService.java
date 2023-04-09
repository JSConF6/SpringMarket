package com.spring.market.service;

import com.spring.market.domain.user.UserMapper;
import com.spring.market.domain.user.dto.SignInDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
