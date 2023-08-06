package com.spring.market.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.market.domain.user.dto.LoginDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)throws AuthenticationException{
        logger.info("JwtAuthenticationFilter : 로그인 시도중");
        try {

            ObjectMapper om = new ObjectMapper();
            LoginDto memberLoginDto = om.readValue(request.getInputStream(), LoginDto.class);


            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(memberLoginDto.getLogin_id(), memberLoginDto.getMember_password());


            // 아이디 비밀번호 체크하여 로직 통과
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            System.out.println("authentication = " + authentication);


            PrincipalDetails principalDetails = (PrincipalDetails)authentication.getPrincipal();
            System.out.println("로그인 정상적! "+principalDetails.getMemberLoginDto().getLogin_id());
            return authentication;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain, Authentication authResult)throws  IOException, ServletException {
        System.out.println("인증이 완료됨");
        PrincipalDetails principalDetails = (PrincipalDetails) authResult.getPrincipal();


    }

}