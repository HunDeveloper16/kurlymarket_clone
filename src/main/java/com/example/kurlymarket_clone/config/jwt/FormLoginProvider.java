package com.example.kurlymarket_clone.config.jwt;

import com.example.kurlymarket_clone.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
public class FormLoginProvider implements AuthenticationProvider{

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        System.out.println("로그인 Provider 를 거침");

        String username = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();

        UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsService.loadUserByUsername(username);

        if(passwordEncoder.matches(password, userDetails.getPassword())){
            return new UsernamePasswordAuthenticationToken(userDetails, null);
        }else{
            throw new BadCredentialsException("로그인 정보가 잘못됨");
        }
    }
    @Override
    public boolean supports(Class<?> authentication) {                    // token 타입에 따라서 언제 provider를 사용할지 조건을 지정할 수 있다.
        return UsernamePasswordAuthenticationToken.class==authentication; // provider의 supports 값이 false를 리턴하면, provider의 authenticate 메소드가 호출되지 않는다.
    }
}