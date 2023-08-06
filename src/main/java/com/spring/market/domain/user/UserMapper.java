package com.spring.market.domain.user;

import com.spring.market.domain.user.dto.SignInDto;
import com.spring.market.domain.user.dto.UserInfoDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface UserMapper {

    int findMember(SignInDto signInDto);

    void join(SignInDto signInDto);

    Optional<User> findByUsernameAndPassword(User user);

    Optional<User> findByUsername(String username);

    void updateById(User user);

    void withdraw(int id);

    UserInfoDto findById(String login_id);
}
