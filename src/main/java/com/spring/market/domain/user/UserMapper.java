package com.spring.market.domain.user;

import com.spring.market.domain.user.dto.SignInDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface UserMapper {

    int findMember(SignInDto signInDto);

    void join(SignInDto signInDto);

    Optional<User> findByUsernameAndPassword(User user);

    void updateById(User user);

    void withdraw(User user);
}
