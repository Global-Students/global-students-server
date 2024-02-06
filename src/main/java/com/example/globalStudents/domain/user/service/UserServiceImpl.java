package com.example.globalStudents.domain.user.service;

import com.example.globalStudents.domain.user.converter.Converter;
import com.example.globalStudents.domain.user.dto.UserRequestDTO;
import com.example.globalStudents.domain.user.dto.UserResponseDTO;
import com.example.globalStudents.domain.user.entity.TermsEntity;
import com.example.globalStudents.domain.user.entity.UserAgreeEntity;
import com.example.globalStudents.domain.user.entity.UserEntity;
import com.example.globalStudents.domain.user.repository.TermsRepository;
import com.example.globalStudents.domain.user.repository.UserAgreeRepository;
import com.example.globalStudents.domain.user.repository.UserRepository;
import com.example.globalStudents.global.apiPayload.code.status.ErrorStatus;
import com.example.globalStudents.global.apiPayload.exception.handler.ExceptionHandler;
import com.example.globalStudents.global.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {


    private final Converter<UserEntity,UserRequestDTO.JoinDTO,UserResponseDTO.JoinResultDTO> converter;
    private final UserRepository userRepository;
    private final UserAgreeRepository userAgreeRepository;
    private  final TermsRepository termsRepository;
    private final MailService mailService;
    private final RedisUtil redisUtil;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    @Override
    public UserResponseDTO.JoinResultDTO createUser(UserRequestDTO.JoinDTO joinDTO) {
        var userEntity = converter.toEntity(joinDTO);
        var newEntity = userRepository.save(userEntity);
        if(joinDTO.getTerms()){
            TermsEntity termsEntity = termsRepository.findByName("terms").get();
            UserAgreeEntity userAgreeEntity = UserAgreeEntity.builder()
                    .user(newEntity)
                    .terms(termsEntity)
                    .createdAt(LocalDateTime.now())
                    .agree(true)
                    .build();
            userAgreeRepository.save(userAgreeEntity);
        }
        if(joinDTO.getPrivacy()){
            UserAgreeEntity userAgreeEntity = UserAgreeEntity.builder()
                    .user(newEntity)
                    .terms(termsRepository.findByName("privacy").get())
                    .createdAt(LocalDateTime.now())
                    .agree(true)
                    .build();
            userAgreeRepository.save(userAgreeEntity);
        }
        if(joinDTO.getMarketing()){
            UserAgreeEntity userAgreeEntity = UserAgreeEntity.builder()
                    .user(newEntity)
                    .terms(termsRepository.findByName("marketing").get())
                    .createdAt(LocalDateTime.now())
                    .agree(true)
                    .build();
            userAgreeRepository.save(userAgreeEntity);
        }
        return converter.toResponse(newEntity);
    }

    @Override
    public UserResponseDTO.CheckIdResultDTO checkId(String userId) {
        if(userRepository.findByUserId(userId).isEmpty()){
            return UserResponseDTO.CheckIdResultDTO.builder()
                    .userId(userId)
                    .build();
        } else{
            throw new ExceptionHandler(ErrorStatus.JOIN_ID_ALREADY_EXIST);
        }
    }

    @Override
    public UserResponseDTO.CheckNicknameResultDTO checkNickname(String nickname) {
        if(userRepository.findByNickname(nickname).isEmpty()){
            return UserResponseDTO.CheckNicknameResultDTO.builder()
                    .nickname(nickname)
                    .build();
        } else{
            throw new ExceptionHandler(ErrorStatus.JOIN_NICKNAME_ALREADY_EXIST);
        }
    }

    @Override
    public UserResponseDTO.FindIdResultDTO findId(String email) {
        Optional<UserEntity> userEntity = userRepository.findByEmail(email);
        if(userRepository.findByEmail(email).isPresent()){
            mailService.sendFindIdMail(email,userEntity.get().getUserId());
        } else {
            throw new ExceptionHandler(ErrorStatus.EMAIL_NOT_FOUND);
        }
        return UserResponseDTO.FindIdResultDTO.builder()
                .email(email)
                .build();
    }

    @Override
    public UserResponseDTO.FindPasswordResultDTO findPassword(String email) {
        Optional<UserEntity> userEntity = userRepository.findByEmail(email);
        if(userRepository.findByEmail(email).isPresent()){
            mailService.sendFindPasswordMail(email);
        } else {
            throw new ExceptionHandler(ErrorStatus.EMAIL_NOT_FOUND);
        }
        return UserResponseDTO.FindPasswordResultDTO.builder()
                .email(email)
                .build();
    }

    public UserResponseDTO.MailCodeVerificationResultDTO verifyEmailCode(String email, String code) {
        String codeFoundByEmail = redisUtil.getData(email);
        if (codeFoundByEmail == null || !codeFoundByEmail.equals(code)) {
            return UserResponseDTO.MailCodeVerificationResultDTO
                    .builder()
                    .verified(false)
                    .build();
        }
        return UserResponseDTO.MailCodeVerificationResultDTO
                .builder()
                .verified(true)
                .build();
    }

    @Override
    public void resetPassword(String email, String password) {
        Optional<UserEntity> userEntity = userRepository.findByEmail(email);
        if(userEntity.isPresent()){
            var updateEntity = userEntity.get();
            updateEntity.setPassword(bCryptPasswordEncoder.encode(password));
            updateEntity.setUpdatedAt(LocalDateTime.now());
            userRepository.save(updateEntity);
        } else {
            throw new ExceptionHandler(ErrorStatus.EMAIL_NOT_FOUND);
        }
    }

    final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

}
