package com.example.globalStudents.global.auth;

import com.amazonaws.HttpMethod;
import com.example.globalStudents.domain.board.repository.BoardRepository;
import com.example.globalStudents.domain.user.repository.UserRepository;
import com.example.globalStudents.global.auth.filter.AuthenticationAccessDeniedHandler;
import com.example.globalStudents.global.auth.filter.JwtFilter;
import com.example.globalStudents.global.auth.filter.LoginFilter;
import com.example.globalStudents.global.util.JWTUtil;
import com.example.globalStudents.global.util.RedisUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    //AuthenticationManager가 인자로 받을 AuthenticationConfiguraion 객체 생성자 주입
    private final AuthenticationConfiguration authenticationConfiguration;

    private final AuthenticationEntryPoint entryPoint;

    private final JWTUtil jwtUtil;

    private final RedisUtil redisUtil;

    private final AuthenticationAccessDeniedHandler accessDeniedHandler;

    private final ObjectMapper objectMapper;

    private final BoardRepository boardRepository;


    public SecurityConfig(AuthenticationConfiguration authenticationConfiguration, AuthenticationAccessDeniedHandler accessDeniedHandler, RedisUtil redisUtil, AuthenticationEntryPoint entryPoint, JWTUtil jwtUtil, ObjectMapper objectMapper, BoardRepository boardRepository)
    {

        this.authenticationConfiguration = authenticationConfiguration;
        this.accessDeniedHandler = accessDeniedHandler;
        this.redisUtil = redisUtil;
        this.entryPoint = entryPoint;
        this.jwtUtil = jwtUtil;

        this.objectMapper = objectMapper;
        this.boardRepository = boardRepository;
    }

    //AuthenticationManager Bean 등록

    @Bean
    public AuthenticationManager authenticationManager (AuthenticationConfiguration configuration) throws Exception
    {

        return configuration.getAuthenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder () {

        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain (HttpSecurity http) throws Exception {

        http
                .cors(Customizer.withDefaults());
        http
                .csrf((auth) -> auth.disable());

        http
                .formLogin((auth) -> auth.disable());

        http
                .httpBasic((auth) -> auth.disable());

        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/", "/error", "/v3/**", "/swagger-ui/**", "/auth/**", "/user/**", "/refresh", "/health","/search/university","/boards/{board_id}/posts/{post_id}","/boards/{board_id}").permitAll()
                        .requestMatchers("/admin/**","/search/**","/mypage/**", "/friends/**","/boards/**").hasRole("ADMIN")
                        .requestMatchers("/search/**", "/mypage/**","/friends/**","/boards/**").hasRole("USER")
                        .requestMatchers("/mypage/**").hasRole("TEMP")
                        .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                        .anyRequest().authenticated());


        http
                .addFilterBefore(new JwtFilter(jwtUtil), LoginFilter.class);
        //필터 추가 LoginFilter()는 인자를 받음 (AuthenticationManager() 메소드에 authenticationConfiguration 객체를 넣어야 함) 따라서 등록 필요
        http
                .addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration), jwtUtil, objectMapper, boardRepository, redisUtil), UsernamePasswordAuthenticationFilter.class);

        http
                .exceptionHandling()
                .authenticationEntryPoint(entryPoint)
                .accessDeniedHandler(accessDeniedHandler);

        http
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("https://feature-102-sign-up-api--global-students.netlify.app", "https://global-students.netlify.app","https://www.globalstudents.shop", "http://localhost:localhost:3000", "http://localhost:localhost:8080"));
        configuration.setAllowedMethods(Arrays.asList("GET","POST", "PATCH", "DELETE", "OPTIONS" , "PUT"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setExposedHeaders(List.of("*"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }




}
