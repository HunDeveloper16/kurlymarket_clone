package com.example.kurlymarket_clone.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.kurlymarket_clone.dto.LoginRequestDto;
import com.example.kurlymarket_clone.security.UserDetailsImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.Date;

public class FormLoginFilter extends UsernamePasswordAuthenticationFilter {

    public FormLoginFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            ObjectMapper om = new ObjectMapper();

            LoginRequestDto loginRequestDto = om.readValue(request.getInputStream(), LoginRequestDto.class);

            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(), loginRequestDto.getPassword());

            System.out.println("FormLoginFilter를 거침");

            return getAuthenticationManager().authenticate(authenticationToken);

        }
        catch (IOException e){
            throw new RuntimeException("잘못된 로그인 정보입니다.");
//            e.printStackTrace();
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        UserDetailsImpl userDetails = (UserDetailsImpl) authResult.getPrincipal();

        String jwtToken = JWT.create()
                .withSubject(userDetails.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis()+(60000*10*24)))
                .withClaim("id",userDetails.getUser().getId())
                .withClaim("username",userDetails.getUser().getUsername())
                .sign(Algorithm.HMAC512("clone_3team"));

        response.addHeader("Authorization","Bearer "+jwtToken);

        System.out.println("successfulAuthentication메서드를 거침");

    }

//    @Override
//    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
//        System.out.println(failed.getMessage());
//        response.setStatus(400);
//        response.setCharacterEncoding("UTF-8");
//        response.getWriter().write(failed.getMessage());
//
//    }



}