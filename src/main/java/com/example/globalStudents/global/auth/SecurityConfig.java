package com.example.globalStudents.global.auth;

import com.example.globalStudents.domain.user.enums.UserRole;
import com.example.globalStudents.global.auth.filter.AuthenticationAccessDeniedHandler;
import com.example.globalStudents.global.auth.filter.JwtFilter;
import com.example.globalStudents.global.auth.filter.LoginFilter;
import com.example.globalStudents.global.util.JWTUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    //AuthenticationManager가 인자로 받을 AuthenticationConfiguraion 객체 생성자 주입
    private final AuthenticationConfiguration authenticationConfiguration;

    private final AuthenticationAccessDeniedHandler accessDeniedHandler;

    private final AuthenticationEntryPoint entryPoint;

    private final JWTUtil jwtUtil;
    public SecurityConfig(AuthenticationConfiguration authenticationConfiguration, AuthenticationAccessDeniedHandler accessDeniedHandler, AuthenticationEntryPoint entryPoint, JWTUtil jwtUtil) {

        this.authenticationConfiguration = authenticationConfiguration;
        this.accessDeniedHandler = accessDeniedHandler;
        this.entryPoint = entryPoint;
        this.jwtUtil = jwtUtil;
    }

    //AuthenticationManager Bean 등록
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {

        return configuration.getAuthenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {

        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf((auth) -> auth.disable());

        http
                .formLogin((auth) -> auth.disable());

        http
                .httpBasic((auth) -> auth.disable());

        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/", "/error","/v3/**", "/swagger-ui/**","/auth/**","/user/**", "/boards/**").permitAll()
                        .requestMatchers("admin/**").hasAuthority(UserRole.ADMIN.name())
                        .anyRequest().authenticated());

        http
                .addFilterBefore(new JwtFilter(jwtUtil),LoginFilter.class);
        //필터 추가 LoginFilter()는 인자를 받음 (AuthenticationManager() 메소드에 authenticationConfiguration 객체를 넣어야 함) 따라서 등록 필요
        http
                .addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration),jwtUtil), UsernamePasswordAuthenticationFilter.class);

        http
                .exceptionHandling()
                .authenticationEntryPoint(entryPoint)
                .accessDeniedHandler(accessDeniedHandler);

        http
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));


        return http.build();
    }

}
