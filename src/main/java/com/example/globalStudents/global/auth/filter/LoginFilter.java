package com.example.globalStudents.global.auth.filter;

import com.example.globalStudents.domain.board.enums.BoardType;
import com.example.globalStudents.domain.board.repository.BoardRepository;
import com.example.globalStudents.domain.user.dto.UserRequestDTO;
import com.example.globalStudents.domain.user.dto.UserResponseDTO;
import com.example.globalStudents.global.apiPayload.ApiResponse;
import com.example.globalStudents.global.apiPayload.code.status.ErrorStatus;
import com.example.globalStudents.global.auth.jwt.CustomUserDetails;
import com.example.globalStudents.global.util.JWTUtil;
import com.example.globalStudents.global.util.RedisUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.UUID;


public class LoginFilter extends UsernamePasswordAuthenticationFilter {


    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;

    private final ObjectMapper objectMapper;

    private final BoardRepository boardRepository;

    private final RedisUtil redisUtil;

    public LoginFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil, ObjectMapper objectMapper, BoardRepository boardRepository, RedisUtil redisUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.objectMapper = objectMapper;
        this.boardRepository = boardRepository;
        this.redisUtil = redisUtil;
        setFilterProcessesUrl("/auth/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {


        UserRequestDTO.UserLoginDTO loginDTO;

        // AuthenticationServiceException은 unsuccessfulAuthentication 메소드 호출
        if (request.getContentType() == null || !request.getContentType().equals("application/json")) {
            throw new AuthenticationServiceException("Authentication Content-Type not supported: " + request.getContentType());
        }

        try {
            loginDTO = objectMapper.readValue(StreamUtils.copyToString(request.getInputStream(), StandardCharsets.UTF_8), UserRequestDTO.UserLoginDTO.class);
        } catch (IOException e) {
            throw new AuthenticationServiceException("JSON to DTO not compatible");
        }

        String username = loginDTO.getUsername();
        String password = loginDTO.getPassword();

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password, null);

        return authenticationManager.authenticate(authToken);
    }


    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException {
        //UserDetailsS
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

        String username = customUserDetails.getUsername();

        // TODO: 2024/02/13 로그인시 접근가능 게시판 반환
//        Long universityId = customUserDetails.getHostUniversity();
//        Long countryId = customUserDetails.getNationality();
//
//        Long universityBoardId = boardRepository.findByUniversityIdAndType(universityId, BoardType.UNIVERSITY).get();
//        Long universityCountryBoardId = boardRepository.findByUniversityIdAndType(universityId, BoardType.COUNTRY).get();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();

        String role = auth.getAuthority();

        String accessToken = jwtUtil.createJwt(username, role, 1000 * 60L);
        String refreshToken = UUID.randomUUID().toString();

        Cookie refreshCookie = new Cookie("refreshToken", refreshToken);
        refreshCookie.setMaxAge(7 * 24 * 60 * 60);
        response.addCookie(refreshCookie);

        Date expireAt = jwtUtil.getExpireDate(accessToken);
        redisUtil.setDataExpire(refreshToken,username,7 * 24 * 60 * 60L);

        setSuccessResponse(response,accessToken,refreshToken,expireAt);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
        setUnsuccessfulResponse(response);
    }
    private void setSuccessResponse(HttpServletResponse response, String accessToken, String refreshToken, Date expireAt) throws IOException {
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json;charset=UTF-8");
        ObjectMapper mapper = new ObjectMapper();

        UserResponseDTO.LoginResultDTO loginResultDTO = UserResponseDTO.LoginResultDTO.builder()
                .accessToken("Bearer "+accessToken)
                .refreshToken(refreshToken)
                .expireAt(expireAt)
                .build();

        String text = mapper.writeValueAsString(ApiResponse.onCreated(loginResultDTO));

        response.getWriter().print(text);
    }


    private void setUnsuccessfulResponse(HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.setContentType("application/json;charset=UTF-8");
        ObjectMapper mapper = new ObjectMapper();
        String text = mapper.writeValueAsString(ApiResponse.onFailure(ErrorStatus.LOGIN_ERROR.getCode(),ErrorStatus.LOGIN_ERROR.getMessage()+" ",""));

        response.getWriter().print(text);
    }
}
