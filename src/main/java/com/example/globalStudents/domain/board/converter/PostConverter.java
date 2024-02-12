package com.example.globalStudents.domain.board.converter;

import com.example.globalStudents.domain.board.dto.PostRequestDTO;
import com.example.globalStudents.domain.board.dto.PostResponseDTO;
import com.example.globalStudents.domain.board.entity.*;
import com.example.globalStudents.domain.board.enums.PostStatus;
import com.example.globalStudents.domain.board.enums.UserPostReactionType;
import com.example.globalStudents.domain.user.entity.UserEntity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PostConverter {

    public static PostResponseDTO.WritePostResultDTO toWritePostResultDTO(PostEntity post) {
        return PostResponseDTO.WritePostResultDTO.builder()
                .postId(post.getId().toString())
                .build();
    }


    public static PostEntity toPost(PostRequestDTO.WritePostDTO request, UserEntity user, BoardEntity board) {
        return PostEntity.builder()
                .user(user)
                .board(board)
                .uid(user.getUserId())
                .title(request.getTitle())
                .body(request.getContent())
                .view(0)
                .likes(0)
                .bookmarks(0)
                .status(PostStatus.ACTIVE)
                .isAnonymous(request.getIsAnonymous())
                .createdAt(LocalDateTime.now())
                .postImageList(new ArrayList<>())
                .userPostReactionList(new ArrayList<>())
                .reportList(new ArrayList<>())
                .commentList(new ArrayList<>())
                .build();
    }

    public static PostResponseDTO.GetPostResultDTO toGetPostResult(PostEntity post) {
        return PostResponseDTO.GetPostResultDTO.builder()
                .postId(post.getId().toString())
                .title(post.getTitle())
                .date(post.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .userId(post.getUid())
                .userNickname(post.getUser().getNickname())
                .isAnonymous(post.getIsAnonymous())
                .views(post.getView())
                .images(toPostImageList(post.getPostImageList()))
                .likes(post.getLikes())
                .bookmarks(post.getBookmarks())
                .content(post.getBody())
                .comment(toPostCommentList(post.getCommentList()))
                .build();
    }

    public static List<PostResponseDTO.ImageDTO> toPostImageList(List<PostImageEntity> postImageList) {
        return postImageList.stream()
                .map(postImage -> {
                    return PostResponseDTO.ImageDTO.builder()
                            .imageId(postImage.getId().toString())
                            .fileName(postImage.getFileName())
                            .imageUrl(postImage.getImageUrl())
                            .build();
                }).collect(Collectors.toList());
    }

    public static List<PostResponseDTO.CommentDTO> toPostCommentList(List<CommentEntity> commentList) {
        return commentList.stream()
                .map(comment -> {
                    return PostResponseDTO.CommentDTO.builder()
                            .userId(comment.getUser().getUserId())
                            .nickname(comment.getUser().getNickname())
                            .isAnonymous(comment.getIsAnonymous())
                            .content(comment.getBody())
                            .likes(comment.getLikes())
                            .commentId(comment.getId().toString())
                            .date(comment.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                            .build();
                }).collect(Collectors.toList());
    }

    public static UserPostReactionEntity toUserPostReaction(PostRequestDTO.ReactPostDTO request, UserEntity user, PostEntity post) {
        UserPostReactionType reactionType = UserPostReactionType.LIKE;
        if (request.getReactionType().equals("BOOKMARK")) {
            reactionType = UserPostReactionType.BOOKMARK;
        }
        return UserPostReactionEntity.builder()
                .type(reactionType)
                .user(user)
                .post(post)
                .build();
    }

    public static PostResponseDTO.ReactPostResultDTO toReactPostResultDTO(UserPostReactionEntity userPostReaction) {
        return PostResponseDTO.ReactPostResultDTO.builder()
                .postId(userPostReaction.getPost().getId().toString())
                .build();
    }


}
