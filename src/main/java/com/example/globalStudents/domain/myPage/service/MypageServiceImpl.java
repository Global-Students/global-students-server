package com.example.globalStudents.domain.myPage.service;

import com.example.globalStudents.domain.board.entity.PostEntity;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

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
                .map(userPostReaction -> userPostReaction.getId().getPost())
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
}
