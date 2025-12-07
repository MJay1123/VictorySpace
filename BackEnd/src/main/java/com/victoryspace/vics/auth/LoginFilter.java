package com.victoryspace.vics.auth;

import com.victoryspace.vics.auth.dto.CustomUserDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public LoginFilter(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        super.setFilterProcessesUrl("/api/auth/login"); // 로그인 요청 URL 변경
        setUsernameParameter("email");
        setPasswordParameter("password");
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response){
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(email, password);
        return authenticationManager.authenticate(authToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authentication) throws IOException {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        String email = customUserDetails.getUsername();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority grantedAuthority = iterator.next();

        String role = grantedAuthority.getAuthority();
        String token = jwtUtil.createToken(email, role, 1000 * 60 * 60 * 10L);     // 10시간

        response.addHeader("Authorization", "Bearer " + token);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        // 🔥 프론트가 원하는 JSON 응답
        response.getWriter().write(
                "{ \"email\": \"" + email + "\", \"role\": \"" + role + "\", \"token\": \"" + token + "\" }"
        );
    }

    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) {
        response.setStatus(401);
    }
}
