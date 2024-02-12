package com.example.globalStudents.domain.user.service;

import com.example.globalStudents.domain.user.converter.UserConverter;
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
import com.example.globalStudents.global.util.JWTUtil;
import com.example.globalStudents.global.util.RedisUtil;
import com.univcert.api.UnivCert;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    final String key = "a28672dd-bb43-4601-a227-c5ce571d23e5";
    private final UserConverter<UserEntity,UserRequestDTO.JoinDTO,UserResponseDTO.JoinResultDTO> converter;
    private final UserRepository userRepository;
    private final UserAgreeRepository userAgreeRepository;
    private  final TermsRepository termsRepository;
    private final MailService mailService;
    private final RedisUtil redisUtil;
    private final JWTUtil jwtUtil;
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
            throw new ExceptionHandler(ErrorStatus.FIND_ID_EMAIL_NOT_FOUND);
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
            throw new ExceptionHandler(ErrorStatus.FIND_PASSWORD_EMAIL_NOT_FOUND);
        }
        return UserResponseDTO.FindPasswordResultDTO.builder()
                .email(email)
                .build();
    }

    public UserResponseDTO.MailCodeVerificationResultDTO verifyEmailCode(String email, String code) {
        String codeFoundByEmail = redisUtil.getData(email);

        if(codeFoundByEmail == null) {
            throw new ExceptionHandler(ErrorStatus.FIND_PASSWORD_EXPIRED_CODE);
        } else if (!codeFoundByEmail.equals(code)) {
            throw new ExceptionHandler(ErrorStatus.FIND_PASSWORD_INVALID_CODE);
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
            throw new ExceptionHandler(ErrorStatus.FIND_PASSWORD_EMAIL_NOT_FOUND);
        }
    }

    @Override
    public UserResponseDTO.UniversityEmailResultDTO certifyUniversity(String email, String university) {
        try{
            Boolean success = (Boolean) UnivCert.certify(key,email,university,false).get("success");
            if(success){
                return UserResponseDTO.UniversityEmailResultDTO.builder()
                        .complete(true)
                        .build();
            } else {
                throw new ExceptionHandler(ErrorStatus._BAD_REQUEST);
            }
        } catch (IOException e){
            throw new ExceptionHandler(ErrorStatus._INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public UserResponseDTO.UniversityEmailVerificationResultDTO certifyCode(String email, String university, String code) {
        try{
            Boolean success = (Boolean) UnivCert.certifyCode(key,email,university,Integer.parseInt(code)).get("success");
            if(success){
                return UserResponseDTO.UniversityEmailVerificationResultDTO.builder()
                        .university(university)
                        .build();
            }else{
                throw new ExceptionHandler(ErrorStatus._BAD_REQUEST);
            }
        } catch (IOException e){
            throw new ExceptionHandler(ErrorStatus._INTERNAL_SERVER_ERROR);
        }

    }

    public void logout(HttpServletRequest request){
        String accessToken = request.getHeader("Authorization");

        accessToken = accessToken.split(" ")[1];

        if(!redisUtil.existData(accessToken)){
            redisUtil.setDataExpire(accessToken,"true",60*60*60L);
        } else {
            throw new ExceptionHandler(ErrorStatus.LOGGED_OUT);
        }
    }


}
