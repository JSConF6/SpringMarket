package com.spring.market.config;

import com.spring.market.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class JwtAuthorizationFilter extends BasicAuthenticationFilter {


    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserService userService) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        //super.doFilterInternal(request, response, chain);
        logger.info("JwtAuthorizationFilter : 토큰 검증 필터");

       /* // 서명이 정상적으로 됨
        if(mem_no != null){
            Member member = memberService.findInfoByMemNo(Integer.parseInt(mem_no));
            memberLoginDto.setLogin_id(member.getLogin_id());
            memberLoginDto.setMember_password(member.getMem_password());
            PrincipalDetails principalDetails = new PrincipalDetails(memberLoginDto);

            // JWT 토큰 서명을 통해서 서명이 정상이면 Authentication 객체를 만들어 준다
            Authentication authentication =
                    new UsernamePasswordAuthenticationToken(principalDetails,null, principalDetails.getAuthorities());
            // 강제로 시큐리티의 세션에 접근해서 Authentication 객체를 저장
            SecurityContextHolder.getContext().setAuthentication(authentication);

        }*/
        logger.info("JwtAuthorizationFilter 토큰 검증 완료");
        chain.doFilter(request,response);
    }
}
