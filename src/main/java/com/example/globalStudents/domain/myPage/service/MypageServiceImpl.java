package com.example.globalStudents.domain.myPage.service;

import com.example.globalStudents.domain.board.entity.PostEntity;
import com.example.globalStudents.domain.board.entity.UserPostReactionEntity;
import com.example.globalStudents.domain.board.enums.UserPostReactionType;
import com.example.globalStudents.domain.board.repository.PostRepository;
import com.example.globalStudents.domain.board.repository.UserPostReactionRepository;
import com.example.globalStudents.domain.myPage.dto.MypageRequestDTO;
import com.example.globalStudents.domain.myPage.dto.MypageResponseDTO;
import com.example.globalStudents.domain.myPage.entity.UserImageEntity;
import com.example.globalStudents.domain.myPage.enums.ImageType;
import com.example.globalStudents.domain.myPage.repository.UserImageRepository;
import com.example.globalStudents.domain.user.entity.UserEntity;
import com.example.globalStudents.domain.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
    public MypageResponseDTO.MypageDTO getMyPage(Long userId, MypageRequestDTO request) {
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        // 사용자가 작성한 게시글 5개 조회
        PageRequest writtenPageRequest = PageRequest.of(0, 5); // 첫 번째 페이지의 5개 항목
        List<PostEntity> writtenPosts = postRepository.findByUserId(userId, writtenPageRequest).getContent();
        // 사용자가 좋아요한 게시물 5개 조회
        PageRequest favoritePageRequest = PageRequest.of(0, 5); // 첫 번째 페이지의 5개 항목
        List<PostEntity> favoritePosts = userPostReactionRepository.findByUserIdAndType(userId, UserPostReactionType.LIKE, favoritePageRequest)
                .getContent()
                .stream()
                .map(UserPostReactionEntity::getPost)
                .collect(Collectors.toList());

        // 프로필 사진 조회
        Optional<UserImageEntity> profileImage = userImageRepository.findByUserIdAndType(userId, ImageType.Profile).stream().findFirst();
        // 배경 사진 조회
        Optional<UserImageEntity> backgroundImage = userImageRepository.findByUserIdAndType(userId, ImageType.Background).stream().findFirst();

        return MypageResponseDTO.MypageDTO.builder()
                .nickname(user.getNickname())
                .introduction(user.getIntroduction())
                .host_university(String.valueOf(user.getHostUniversity()))
                .nationality(String.valueOf(user.getNationality()))
                .major(user.getMajor())
                .writtenPostList(writtenPosts)
                .favoritePostList(favoritePosts)
                .profilePhotoId(profileImage.map(UserImageEntity::getId).orElse(null))
                .backgroundPhotoId(backgroundImage.map(UserImageEntity::getId).orElse(null))
                .build();
    }
    @Override
    public MypageResponseDTO.MypageInfoDTO getUserInfo(Long userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        return MypageResponseDTO.MypageInfoDTO.builder()
                .userId(user.getUserId())
                .password(user.getPassword())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .birth(String.valueOf(user.getBirth()))
                .nickname(user.getNickname())
                .nationality(String.valueOf(user.getNationality()))
                .hostCountry(String.valueOf(user.getHostCountry()))
                .hostUniversity(String.valueOf(user.getHostUniversity()))
                .major(user.getMajor())
                .phone(user.getPhone())
                .email(user.getEmail())
                .build();
    }
    @Override
    public MypageResponseDTO.MypageProfileDTO getUserProfile(Long userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        Optional<UserImageEntity> profileImage = userImageRepository.findByUserIdAndType(userId, ImageType.Profile).stream().findFirst();
        // 배경 사진 조회
        Optional<UserImageEntity> backgroundImage = userImageRepository.findByUserIdAndType(userId, ImageType.Background).stream().findFirst();

        return MypageResponseDTO.MypageProfileDTO.builder()
                .nickname(user.getNickname())
                .nationality(String.valueOf(user.getNationality()))
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .birth(user.getBirth().toString())
                .hostCountry(String.valueOf(user.getHostCountry()))
                .hostUniversity(String.valueOf(user.getHostUniversity()))
                .major(user.getMajor())
                .introduction(user.getIntroduction())
                .skill(user.getSkill())
                .profilePhotoId(profileImage.map(UserImageEntity::getId).orElse(null))
                .backgroundPhotoId(backgroundImage.map(UserImageEntity::getId).orElse(null))
                .build();
    }

    @Override
    @Transactional
    public MypageRequestDTO.MypageInfoUpdateDTO updateUserProfile(Long userId, MypageRequestDTO.MypageInfoUpdateDTO requestDTO) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        // Update fields if they are not null and not empty
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
    public MypageRequestDTO.MypageProfileUpdateDTO updateProfilePrivacy(Long userId, MypageRequestDTO.MypageProfileUpdateDTO requestDTO) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        user.setNamePrivacy(requestDTO.getNamePrivacy());
        user.setBirthPrivacy(requestDTO.getBirthPrivacy());
        user.setUniversityPrivacy(requestDTO.getUniversityPrivacy());
        user.setMajorPrivacy(requestDTO.getMajorPrivacy());

        userRepository.save(user);
        return requestDTO;
    }
    @Override
    public Page<PostEntity> findPostsByUserId(Long userId, Pageable pageable) {
        return postRepository.findByUserId(userId, pageable);
    }
    @Override
    public Page<UserPostReactionEntity> findBookmarkedPostsByUserId(Long userId, Pageable pageable) {
        return userPostReactionRepository.findByUserIdAndType(userId, UserPostReactionType.LIKE, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Long findUIdByUserId(String userId) {
        UserEntity user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("User not found with username: " + userId));
        return user.getId();
    }
}
