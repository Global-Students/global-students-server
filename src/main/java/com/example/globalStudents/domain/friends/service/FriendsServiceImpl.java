package com.example.globalStudents.domain.friends.service;

import com.example.globalStudents.domain.board.entity.PostEntity;
import com.example.globalStudents.domain.board.entity.UserPostReactionEntity;
import com.example.globalStudents.domain.board.enums.UserPostReactionType;
import com.example.globalStudents.domain.board.repository.PostRepository;
import com.example.globalStudents.domain.board.repository.UserPostReactionRepository;
import com.example.globalStudents.domain.friends.dto.FriendsResponseDTO;
import com.example.globalStudents.domain.friends.entity.FriendEntity;
import com.example.globalStudents.domain.friends.repository.FriendRepository;
import com.example.globalStudents.domain.myPage.entity.UserImageEntity;
import com.example.globalStudents.domain.myPage.enums.ImageType;
import com.example.globalStudents.domain.myPage.repository.UserImageRepository;
import com.example.globalStudents.domain.user.entity.UserEntity;
import com.example.globalStudents.domain.user.repository.UserRepository;
import com.example.globalStudents.global.apiPayload.code.status.ErrorStatus;
import com.example.globalStudents.global.apiPayload.exception.handler.ExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FriendsServiceImpl implements FriendsService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FriendRepository friendRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserImageRepository userImageRepository;
    @Autowired
    private UserPostReactionRepository userPostReactionRepository;
    @Override
    @Transactional
    public List<FriendsResponseDTO.FriendsDTO> getFriendsPage(String userId){
        UserEntity user = userRepository.findByUserId(userId)
                .orElseThrow(()-> new ExceptionHandler(ErrorStatus._BAD_REQUEST));
        List<FriendEntity> friendships = friendRepository.findByFromUser(user);

        List<FriendsResponseDTO.FriendsDTO> friends = friendships.stream()
                .map(FriendEntity::getToUser)
                .map(toUser -> new FriendsResponseDTO.FriendsDTO(
                        toUser.getNickname(),
                        toUser.getNationality().getName(),
                        toUser.getMajor(),
                        toUser.getIntroduction()
                ))
                .toList();
        return friends;
    }

    @Override
    @Transactional
    public FriendsResponseDTO.FriendProfileDTO getFriendProfile(String userId, String friend_id) {
        UserEntity user = userRepository.findByUserId(userId)
                .orElseThrow(()-> new ExceptionHandler(ErrorStatus._BAD_REQUEST));
        UserEntity friend = userRepository.findByUserId(friend_id)
                .orElseThrow(()-> new ExceptionHandler(ErrorStatus._BAD_REQUEST));

        PageRequest writtenPageRequest = PageRequest.of(0, 5);
        List<PostEntity> writtenPosts = postRepository.findByUser_UserId(friend_id, writtenPageRequest).getContent();

        PageRequest favoritePageRequest = PageRequest.of(0, 5);
        List<PostEntity> favoritePosts = userPostReactionRepository.findByUser_UserIdAndType(friend_id, UserPostReactionType.LIKE, favoritePageRequest)
                .getContent()
                .stream()
                .map(UserPostReactionEntity::getPost)
                .collect(Collectors.toList());
        // 프로필 사진 조회
        Optional<UserImageEntity> profileImage = userImageRepository.findByUser_UserIdAndType(userId, ImageType.Profile).stream().findFirst();
        // 배경 사진 조회
        Optional<UserImageEntity> backgroundImage = userImageRepository.findByUser_UserIdAndType(userId, ImageType.Background).stream().findFirst();

        return FriendsResponseDTO.FriendProfileDTO.builder()
                .nickname(friend.getNickname())
                .introduction(friend.getIntroduction())
                .host_university(String.valueOf(friend.getHostUniversity()))
                .nationality(String.valueOf(friend.getNationality()))
                .major(user.getMajor())
                .writtenPostList(writtenPosts)
                .favoritePostList(favoritePosts)
                .profilePhotoId(profileImage.map(UserImageEntity::getId).orElse(null))
                .backgroundPhotoId(backgroundImage.map(UserImageEntity::getId).orElse(null))
                .build();
    }
}
