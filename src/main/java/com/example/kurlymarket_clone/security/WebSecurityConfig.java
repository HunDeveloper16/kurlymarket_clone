package com.example.kurlymarket_clone.security;

import com.example.kurlymarket_clone.config.jwt.FormLoginFilter;
import com.example.kurlymarket_clone.config.jwt.JwtLoginFilter;
import com.example.kurlymarket_clone.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;



@Configuration
@EnableWebSecurity // 스프링 Security 지원을 가능하게 함
@EnableGlobalMethodSecurity(securedEnabled = true)
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserRepository userRepository;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }


    @Override
    public void configure(WebSecurity web) {
// h2-console 사용에 대한 허용 (CSRF, FrameOptions 무시)
        web
                .ignoring()
                .antMatchers("/h2-console/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.cors().configurationSource(corsConfigurationSource());
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .formLogin().disable()
                .httpBasic().disable()
                .addFilter(new FormLoginFilter(authenticationManager()))
                .addFilter(new JwtLoginFilter(authenticationManager(), userRepository))
                .authorizeRequests()
                // api 요청 접근허용
                .antMatchers("/user/**").permitAll()
                .antMatchers("/api/cart/**")
                .access("hasRole('ROLE_USER')")
//                .antMatchers("**").permitAll()
//                .antMatchers("/").permitAll()
//                .antMatchers(HttpMethod.GET,"/api/post/**").permitAll()
//                .antMatchers(HttpMethod.GET, "/api/reply/**").permitAll()
                .anyRequest().permitAll();

    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true) ; // 내 서버가 응답을 할 때 json을 자바스크립트에서 처리할 수 있게 할지 설정
        configuration.addAllowedOriginPattern("*");
        configuration.addAllowedOrigin("프론트 주소"); // 배포 시
        // 수정 필요
        configuration.addAllowedOrigin("http://localhost:3000"); //  local 테스트 시
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");
        configuration.addExposedHeader("Authorization");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}



