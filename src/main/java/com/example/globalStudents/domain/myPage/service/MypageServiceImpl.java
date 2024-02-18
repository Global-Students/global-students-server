package com.example.globalStudents.domain.myPage.service;

import com.example.globalStudents.domain.board.entity.PostEntity;
import com.example.globalStudents.domain.board.entity.UserPostReactionEntity;
import com.example.globalStudents.domain.board.enums.UserPostReactionType;
import com.example.globalStudents.domain.board.repository.PostRepository;
import com.example.globalStudents.domain.board.repository.UserPostReactionRepository;
import com.example.globalStudents.domain.myPage.converter.MypageConverter;
import com.example.globalStudents.domain.myPage.dto.MypageRequestDTO;
import com.example.globalStudents.domain.myPage.dto.MypageResponseDTO;
import com.example.globalStudents.domain.myPage.entity.UserImageEntity;
import com.example.globalStudents.domain.myPage.enums.ImageType;
import com.example.globalStudents.domain.myPage.repository.UserImageRepository;
import com.example.globalStudents.domain.user.entity.UserEntity;
import com.example.globalStudents.domain.user.repository.UserRepository;
import com.example.globalStudents.global.apiPayload.code.status.ErrorStatus;
import com.example.globalStudents.global.apiPayload.exception.handler.ExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MypageServiceImpl implements MypageService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserImageRepository userImageRepository;

    @Autowired
    private UserPostReactionRepository userPostReactionRepository;

    @Override
    @Transactional
    public MypageResponseDTO.MypageDTO getMyPage(String userId) {
        UserEntity user = userRepository.findByUserId(userId)
                .orElseThrow(()-> new ExceptionHandler(ErrorStatus._BAD_REQUEST));
        // 사용자가 작성한 게시글 5개 조회
        PageRequest writtenPageRequest = PageRequest.of(0, 5); // 첫 번째 페이지의 5개 항목
        List<MypageResponseDTO.PostDTO> writtenPosts = postRepository.findByUser_UserId(userId, writtenPageRequest)
                .getContent()
                .stream()
                .map(
                        post -> {
                            return MypageConverter.toPostDTO(post);
                        }
                ).collect(Collectors.toList());
        // 사용자가 좋아요한 게시물 5개 조회
        PageRequest favoritePageRequest = PageRequest.of(0, 5); // 첫 번째 페이지의 5개 항목
        List<MypageResponseDTO.PostDTO> favoritePosts = userPostReactionRepository.findByUser_UserIdAndType(userId, UserPostReactionType.LIKE, favoritePageRequest)
                .getContent()
                .stream()
                .map(
                        userPostReaction -> {
                            return MypageConverter.toPostDTO(userPostReaction.getPost());
                        }
                )
                .collect(Collectors.toList());

        // 프로필 사진 조회
        Optional<UserImageEntity> profileImage = userImageRepository.findByUser_UserIdAndType(userId, ImageType.Profile).stream().findFirst();
        // 배경 사진 조회
        Optional<UserImageEntity> backgroundImage = userImageRepository.findByUser_UserIdAndType(userId, ImageType.Background).stream().findFirst();

        return MypageResponseDTO.MypageDTO.builder()
                .nickname(user.getNickname())
                .introduction(user.getIntroduction())
                .host_university(user.getHostUniversity().getName())
                .nationality(user.getNationality().getName())
                .major(user.getMajor())
                .writtenPostList(writtenPosts)
                .favoritePostList(favoritePosts)
                .profilePhotoId(profileImage.map(UserImageEntity::getId).orElse(null))
                .backgroundPhotoId(backgroundImage.map(UserImageEntity::getId).orElse(null))
                .build();
    }
    @Override
    @Transactional
    public MypageResponseDTO.MypageInfoDTO getUserInfo(String userId) {
        UserEntity user = userRepository.findByUserId(userId)
                .orElseThrow(()-> new ExceptionHandler(ErrorStatus._BAD_REQUEST));

        return MypageResponseDTO.MypageInfoDTO.builder()
                .userId(user.getUserId())
                .password(user.getPassword())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .birth(String.valueOf(user.getBirth()))
                .nickname(user.getNickname())
                .nationality(user.getNationality().getName())
                .hostCountry(user.getHostCountry().getName())
                .hostUniversity(user.getHostUniversity().getName())
                .major(user.getMajor())
                .phone(user.getPhone())
                .email(user.getEmail())
                .build();
    }
    @Override
    @Transactional
    public MypageResponseDTO.MypageProfileDTO getUserProfile(String userId) {
        UserEntity user = userRepository.findByUserId(userId)
                .orElseThrow(()-> new ExceptionHandler(ErrorStatus._BAD_REQUEST));

        Optional<UserImageEntity> profileImage = userImageRepository.findByUser_UserIdAndType(userId, ImageType.Profile).stream().findFirst();
        // 배경 사진 조회
        Optional<UserImageEntity> backgroundImage = userImageRepository.findByUser_UserIdAndType(userId, ImageType.Background).stream().findFirst();

        return MypageResponseDTO.MypageProfileDTO.builder()
                .nickname(user.getNickname())
                .nationality(user.getNationality().getName())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .birth(user.getBirth().toString())
                .hostCountry(user.getHostCountry().getName())
                .hostUniversity(user.getHostUniversity().getName())
                .major(user.getMajor())
                .introduction(user.getIntroduction())
                .skill(user.getSkill())
                .profilePhotoId(profileImage.map(UserImageEntity::getId).orElse(null))
                .backgroundPhotoId(backgroundImage.map(UserImageEntity::getId).orElse(null))
                .build();
    }

    @Override
    @Transactional
    public MypageRequestDTO.MypageInfoUpdateDTO updateUserInfo(String userId, MypageRequestDTO.MypageInfoUpdateDTO requestDTO) {
        UserEntity user = userRepository.findByUserId(userId)
                .orElseThrow(()-> new ExceptionHandler(ErrorStatus._BAD_REQUEST));

        if (requestDTO.getPassword() != null && !requestDTO.getPassword().isEmpty()) {
            user.setPassword(requestDTO.getPassword());
        }
        if (requestDTO.getBirth() != null && !requestDTO.getBirth().isEmpty()) {
            user.setBirth(LocalDate.parse(requestDTO.getBirth()));
        }
        if (requestDTO.getNickname() != null && !requestDTO.getNickname().isEmpty()) {
            user.setNickname(requestDTO.getNickname());
        }
        if (requestDTO.getPhone() != null && !requestDTO.getPhone().isEmpty()) {
            user.setPhone(requestDTO.getPhone());
        }
        if (requestDTO.getEmail() != null && !requestDTO.getEmail().isEmpty()) {
            user.setEmail(requestDTO.getEmail());
        }
        user.setPhonePrivacy(requestDTO.getPhonePrivacy());
        user.setEmailPrivacy(requestDTO.getEmailPrivacy());

        userRepository.save(user);
        return requestDTO;
    }
    @Override
    @Transactional
    public MypageRequestDTO.MypageProfileUpdateDTO updateProfilePrivacy(String userId, MypageRequestDTO.MypageProfileUpdateDTO requestDTO) {
        UserEntity user = userRepository.findByUserId(userId)
                .orElseThrow(()-> new ExceptionHandler(ErrorStatus._BAD_REQUEST));

        user.setNamePrivacy(requestDTO.getNamePrivacy());
        user.setBirthPrivacy(requestDTO.getBirthPrivacy());
        user.setUniversityPrivacy(requestDTO.getUniversityPrivacy());
        user.setMajorPrivacy(requestDTO.getMajorPrivacy());

        userRepository.save(user);
        return requestDTO;
    }
    @Override
    @Transactional
    public Page<PostEntity> findPostsByUserId(String userId, Pageable pageable) {
        return postRepository.findByUser_UserId(userId, pageable);
    }
    @Override
    @Transactional
    public Page<UserPostReactionEntity> findBookmarkedPostsByUserId(String userId, Pageable pageable) {
        return userPostReactionRepository.findByUser_UserIdAndType(userId, UserPostReactionType.LIKE, pageable);
    }
}
