package com.example.kurlymarket_clone.config.jwt;//package com.sparta.springweb.config.jwt;
//
//import com.auth0.jwt.JWT;
//import com.auth0.jwt.JWTVerifier;
//import com.auth0.jwt.algorithms.Algorithm;
//import com.auth0.jwt.interfaces.DecodedJWT;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.sparta.springweb.model.User;
//import com.sparta.springweb.repository.UserRepository;
//import com.sparta.springweb.security.UserDetailsImpl;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.tomcat.util.http.parser.Authorization;
//import org.springframework.http.MediaType;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.HashMap;
//import java.util.Map;
//
//import static java.util.Arrays.stream;
//import static org.springframework.http.HttpHeaders.AUTHORIZATION;
//import static org.springframework.http.HttpStatus.FORBIDDEN;
//
//@Slf4j
//@RequiredArgsConstructor
//public class CustomAuthorizationFilter extends OncePerRequestFilter {
//
//    private final UserRepository userRepository;
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        if(request.getServletPath().equals("/login") || request.getServletPath().equals("/api/token/refresh")){
//            System.out.println("CustomAuthorizationFilter를 거침");
//            filterChain.doFilter(request,response);
//        } else{
//            String authorizationHeader = request.getHeader("Authorization");
//
//            if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
//
//                try{
//                    String token = authorizationHeader.substring("Bearer ".length());
//                    Algorithm algorithm = Algorithm.HMAC512("clone_3team".getBytes());
//
//                    JWTVerifier verifier = JWT.require(algorithm).build();
//
//                    DecodedJWT decodedJWT = verifier.verify(token);
//
//                    String username = decodedJWT.getSubject();
//                    String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
//                    Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
//                    stream(roles).forEach(role -> {
//                        authorities.add(new SimpleGrantedAuthority(role));
//                    });
//                  UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
//                            new UsernamePasswordAuthenticationToken(username, null, authorities);
//
//                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
//                    User user = userRepository.findByUsername(username)
//                            .orElseThrow( ()-> new IllegalArgumentException("username 이 없습니다"));
//
//                    filterChain.doFilter(request, response);
//
//                }catch (Exception e){
//                    log.error("Error logging in: {}", e.getMessage());
//                    response.setHeader("error", e.getMessage());
//                    response.setStatus(FORBIDDEN.value());
//
//                    Map<String, String> error = new HashMap<>();
//                    error.put("error_message", e.getMessage());
//                    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//                    new ObjectMapper().writeValue(response.getOutputStream(), error);
//                }
//            }else{
//                filterChain.doFilter(request, response);
//
//            }
//        }
//    }
//}
