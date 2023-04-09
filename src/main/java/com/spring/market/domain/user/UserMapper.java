package com.spring.market.domain.user;

import com.spring.market.domain.user.dto.SignInDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    int findMember(SignInDto signInDto);

    void join(SignInDto signInDto);
}
