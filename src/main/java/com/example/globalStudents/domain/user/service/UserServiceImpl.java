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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final Converter<UserEntity,UserRequestDTO.JoinDTO,UserResponseDTO.JoinResultDTO> converter;
    private final UserRepository userRepository;
    private final UserAgreeRepository userAgreeRepository;
    private  final TermsRepository termsRepository;

//    @Override
//    public UserResponseDTO.JoinResultDTO createUser(UserRequestDTO.JoinDTO joinDTO) {
//        var userEntity = converter.toEntity(joinDTO);
//        var newEntity = userRepository.save(userEntity);
//        if(joinDTO.getTerms()){
//            TermsEntity termsEntity = termsRepository.findByName("terms").get();
//            UserAgreeEntity userAgreeEntity = UserAgreeEntity.builder()
//                    .user(newEntity)
//                    .terms(termsEntity)
//                    .createdAt(LocalDateTime.now())
//                    .agree(true)
//                    .build();
//            userAgreeRepository.save(userAgreeEntity);
//        }
//        if(joinDTO.getPrivacy()){
//            UserAgreeEntity userAgreeEntity = UserAgreeEntity.builder()
//                    .user(newEntity)
//                    .terms(termsRepository.findByName("privacy").get())
//                    .createdAt(LocalDateTime.now())
//                    .agree(true)
//                    .build();
//            userAgreeRepository.save(userAgreeEntity);
//        }
//        if(joinDTO.getMarketing()){
//            UserAgreeEntity userAgreeEntity = UserAgreeEntity.builder()
//                    .user(newEntity)
//                    .terms(termsRepository.findByName("marketing").get())
//                    .createdAt(LocalDateTime.now())
//                    .agree(true)
//                    .build();
//            userAgreeRepository.save(userAgreeEntity);
//        }
//        return converter.toResponse(newEntity);
//    }

    @Override
    public UserResponseDTO.CheckIdDTO checkId(String userId) {
        if(userRepository.findByUserId(userId).isEmpty()){
            return UserResponseDTO.CheckIdDTO.builder()
                    .userId(userId)
                    .build();
        } else{
            throw new ExceptionHandler(ErrorStatus.JOIN_ID_ALREADY_EXIST);
        }
    }

    @Override
    public UserResponseDTO.CheckNicknameDTO checkNickname(String nickname) {
        if(userRepository.findByNickname(nickname).isEmpty()){
            return UserResponseDTO.CheckNicknameDTO.builder()
                    .nickname(nickname)
                    .build();
        } else{
            throw new ExceptionHandler(ErrorStatus.JOIN_NICKNAME_ALREADY_EXIST);
        }
    }
}
