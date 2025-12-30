package com.victoryspace.vics.auth;

import com.victoryspace.vics.auth.dto.CustomUserDetails;
import com.victoryspace.vics.member.command.domain.aggregate.MemberEntity;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;

    public JwtFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String requestURI = request.getRequestURI();

        if (requestURI.startsWith("/api/auth")) {
            filterChain.doFilter(request, response);
            return;
        }

        String authorization = request.getHeader("Authorization");
        if(authorization == null || !authorization.startsWith("Bearer ")){
            System.out.println("token is null");
            filterChain.doFilter(request, response);
            return;
        }

        String token = authorization.substring(7);
        if(!jwtUtil.validateToken(token)){
            System.out.println("token is invalid");
            filterChain.doFilter(request, response);
            return;
        }
        if(jwtUtil.isExpired(token)){
            System.out.println("token is expired");
            filterChain.doFilter(request, response);
            return;
        }

        Integer memberId = jwtUtil.getMemberId(token);
        String email = jwtUtil.getEmail(token);
        String role = jwtUtil.getRole(token);

        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setId(memberId);
        memberEntity.setEmail(email);
        memberEntity.setPassword("temp");
        memberEntity.setRole(role);

        CustomUserDetails customUserDetails = new CustomUserDetails(memberEntity);

        Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);

    }
}
