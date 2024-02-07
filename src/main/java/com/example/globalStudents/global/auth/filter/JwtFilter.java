package com.example.globalStudents.global.auth.filter;

import com.example.globalStudents.domain.user.entity.UserEntity;
import com.example.globalStudents.domain.user.enums.UserRole;
import com.example.globalStudents.global.auth.jwt.CustomUserDetails;
import com.example.globalStudents.global.util.JWTUtil;
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

    private final JWTUtil jwtUtil;

    public JwtFilter(JWTUtil jwtUtil) {

        this.jwtUtil = jwtUtil;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {
            String authorization= request.getHeader("Authorization");

            if (authorization == null || !authorization.startsWith("Bearer ")) {

                filterChain.doFilter(request, response);

                return;
            }

            String token = authorization.split(" ")[1];

            if (jwtUtil.isExpired(token)) {

                filterChain.doFilter(request, response);

                return;
            }


            String username = jwtUtil.getUsername(token);
            String role = jwtUtil.getRole(token);


            UserEntity userEntity = UserEntity.builder()
                    .userId(username)
                    .password("temppassword")
                    .role(UserRole.valueOf(role))
                    .build();


            CustomUserDetails customUserDetails = new CustomUserDetails(userEntity);


            Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authToken);

        } catch (Exception e){
            request.setAttribute("exception", e);
        }

        filterChain.doFilter(request, response);
    }
}
