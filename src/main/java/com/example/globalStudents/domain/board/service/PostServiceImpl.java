package com.example.globalStudents.domain.board.service;

import com.example.globalStudents.domain.board.converter.PostConverter;
import com.example.globalStudents.domain.board.dto.PostRequestDTO;
import com.example.globalStudents.domain.board.dto.PostResponseDTO;
import com.example.globalStudents.domain.board.entity.BoardEntity;
import com.example.globalStudents.domain.board.entity.PostEntity;
import com.example.globalStudents.domain.board.entity.PostImageEntity;
import com.example.globalStudents.domain.board.entity.UserPostReactionEntity;
import com.example.globalStudents.domain.board.enums.PostStatus;
import com.example.globalStudents.domain.board.enums.UserPostReactionType;
import com.example.globalStudents.domain.board.repository.BoardRepository;
import com.example.globalStudents.domain.board.repository.PostImageRepository;
import com.example.globalStudents.domain.board.repository.PostRepository;
import com.example.globalStudents.domain.board.repository.UserPostReactionRepository;
import com.example.globalStudents.domain.user.entity.UserEntity;
import com.example.globalStudents.domain.user.repository.UserRepository;
import com.example.globalStudents.global.apiPayload.code.status.ErrorStatus;
import com.example.globalStudents.global.apiPayload.exception.handler.ExceptionHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PostServiceImpl implements PostService{

    private final PostRepository postRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final PostImageRepository postImageRepository;
    private final UserPostReactionRepository userPostReactionRepository;

    @Override
    public PostResponseDTO.WritePostResultDTO writePost(PostRequestDTO.WritePostDTO request, String userId) {

        UserEntity user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new ExceptionHandler(ErrorStatus._UNAUTHORIZED));

        //board_id 확인
        Long boardId;
        try {
            boardId = Long.parseLong(request.getBoardId());
        } catch (Exception e) {
            throw new ExceptionHandler(ErrorStatus.BOARD_BOARD_ID_INVALID);
        }

        BoardEntity board = boardRepository.findById(boardId)
                .orElseThrow(() -> new ExceptionHandler(ErrorStatus.BOARD_BOARD_ID_INVALID));

        PostEntity newPost = PostConverter.toPost(request, user, board);

        postImageMapping(request.getImage(), newPost);

        return PostConverter.toWritePostResultDTO(postRepository.save(newPost));
    }

    public void postImageMapping(List<PostRequestDTO.PostRequestImageDTO> requestImageList, PostEntity post) {
        if (!requestImageList.isEmpty()) {
            List<PostImageEntity> postImageList = requestImageList.stream()
                    .map(imageDTO -> {
                        return postImageRepository.findById(Long.parseLong(imageDTO.getImageId()))
                                .orElseThrow(()-> new ExceptionHandler(ErrorStatus.POST_IMAGE_ID_INVALID));
                    }).collect(Collectors.toList());

            postImageList.forEach(postImage ->{postImage.setPost(post);});
        }
    }

    @Override
    public PostResponseDTO.WritePostResultDTO updatePost(PostRequestDTO.WritePostDTO request, Long postId, String userId) {
        PostEntity post = postRepository.findById(postId)
                .orElseThrow(()-> new ExceptionHandler(ErrorStatus.POST_NOT_FOUND));

        //update 가능한지 체크
        checkPostUpdateAvailable(post, userId);

        Long boardId;
        try {
            boardId = Long.parseLong(request.getBoardId());
        } catch (Exception e) {
            throw new ExceptionHandler(ErrorStatus.BOARD_BOARD_ID_INVALID);
        }

        BoardEntity board = boardRepository.findById(boardId)
                .orElseThrow(() -> new ExceptionHandler(ErrorStatus.BOARD_BOARD_ID_INVALID));

        post.setBoard(board);
        post.setTitle(request.getTitle());
        post.setBody(request.getContent());
        post.setIsAnonymous(request.getIsAnonymous());
        post.setUpdatedAt(LocalDateTime.now());
        postImageMapping(request.getImage(), post);

        return PostConverter.toWritePostResultDTO(postRepository.save(post));
    }

    public void checkPostUpdateAvailable(PostEntity post, String userId) {

        UserEntity user = userRepository.findByUserId(userId).orElseThrow(()->new RuntimeException("User_Not_Found"));

        //게시글 작성자인지 확인
        if (!post.getUser().getId().equals(post.getUser().getId())) {
            throw new ExceptionHandler(ErrorStatus._UNAUTHORIZED);
        }
        //게시글 상태 확인
        if (post.getStatus().equals(PostStatus.DELETED)) {
            throw new ExceptionHandler(ErrorStatus._FORBIDDEN);
        }
    }

    @Override
    public PostResponseDTO.ReactPostResultDTO reactPost(PostRequestDTO.ReactPostDTO request, String userId) {

        UserEntity user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new ExceptionHandler(ErrorStatus._UNAUTHORIZED));

        PostEntity post = postRepository.findById(Long.parseLong(request.getPostId()))
                .orElseThrow(() -> new ExceptionHandler(ErrorStatus.POST_NOT_FOUND));

        UserPostReactionType postReactionType;

        if (request.getReactionType().equals("LIKE")) {
            postReactionType = UserPostReactionType.LIKE;
            checkPostReacted(post, user, postReactionType);
            post.incrementLikes();
        } else if (request.getReactionType().equals("BOOKMARK")) {
            postReactionType = UserPostReactionType.BOOKMARK;
            checkPostReacted(post, user, postReactionType);
            post.incrementBookmarks();
        }
        else {
            throw new ExceptionHandler(ErrorStatus._BAD_REQUEST);
        }
        postRepository.save(post);

        UserPostReactionEntity newPostReaction = PostConverter.toUserPostReaction(request, user, post);

        return PostConverter.toReactPostResultDTO(userPostReactionRepository.save(newPostReaction));
    }

    public void checkPostReacted(PostEntity post, UserEntity user, UserPostReactionType userPostReactionType) {
        if (!userPostReactionRepository.findByPostAndUserAndType(post, user, userPostReactionType).isEmpty()) {
            throw new ExceptionHandler(ErrorStatus.POST_ALREADY_REACTED);
        }
    }

    @Override
    public PostResponseDTO.GetPostResultDTO getPost(Long postId) {
        PostEntity post = postRepository.findById(postId)
                .orElseThrow(()-> new ExceptionHandler(ErrorStatus.POST_NOT_FOUND));

        if (!post.getStatus().equals(PostStatus.ACTIVE)) {
            throw new ExceptionHandler(ErrorStatus.POST_NOT_FOUND);
        }

        post.incrementView();

        return PostConverter.toGetPostResult(postRepository.save(post));
    }

    @Override
    public void deletePost(PostRequestDTO.DeletePostDTO request, String userId) {
        Long postId = Long.parseLong(request.getPostId());

        PostEntity post = postRepository.findById(postId)
                .orElseThrow(() -> new ExceptionHandler(ErrorStatus.POST_NOT_FOUND));

        //작성자 확인
        if (!post.getUid().equals(userId)) {
            throw new ExceptionHandler(ErrorStatus._UNAUTHORIZED);
        }

        post.setStatus(PostStatus.DELETED);
        postRepository.save(post);
    }

}
