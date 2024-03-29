package com.example.globalStudents.domain.user.service;

import com.example.globalStudents.global.apiPayload.code.status.ErrorStatus;
import com.example.globalStudents.global.apiPayload.exception.handler.ExceptionHandler;
import com.example.globalStudents.global.auth.jwt.CustomUserDetails;
import com.example.globalStudents.domain.user.entity.UserEntity;
import com.example.globalStudents.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> userEntity = userRepository.findByUserId(username);

        if(userEntity.isPresent()){
            return new CustomUserDetails(userEntity.get());
        } else {
            throw new UsernameNotFoundException("id not found");
        }
    }
}
