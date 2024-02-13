package com.example.globalStudents.domain.board.controller;

import com.example.globalStudents.domain.board.converter.CommentConverter;
import com.example.globalStudents.domain.board.converter.PostConverter;
import com.example.globalStudents.domain.board.converter.ReportConverter;
import com.example.globalStudents.domain.board.dto.*;
import com.example.globalStudents.domain.board.entity.*;
import com.example.globalStudents.domain.board.service.*;
import com.example.globalStudents.global.apiPayload.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/boards")
public class BoardController {

    private final PostService postService;
    private final CommentService commentService;
    private final ReportService reportService;
    private final BoardService boardService;
    private final PostImageService postImageService;

    //게시판 홈
    @GetMapping("/{board_id}")
    public ApiResponse<BoardResponseDTO.BoardResultDTO> getBoard(
            @PathVariable("board_id") Long boardId,
            @RequestParam(name = "sort", required = false, defaultValue = "latest") String sortingType,
            @RequestParam(name = "page_number", required = false, defaultValue = "0") int page,
            @RequestParam(name = "q", required = false) String keyword) {


        return ApiResponse.onSuccess(boardService.getBoardHome(boardId, sortingType, page, keyword));
    }

    //게시글 조회
    @GetMapping("/{board_id}/posts/{post_id}")
    public ApiResponse<PostResponseDTO.GetPostResultDTO> getPost(
            @PathVariable("board_id") Long boardId, @PathVariable("post_id") Long postId) {

        PostEntity post = postService.getPost(postId);

        return ApiResponse.onSuccess(PostConverter.toGetPostResult(post));

    }

    //게시글 작성
    @PostMapping("/post/write")
    public ApiResponse<PostResponseDTO.WritePostResultDTO> writePost(
            @RequestBody @Valid PostRequestDTO.WritePostDTO request) {

        PostEntity post = postService.writePost(request);

        return ApiResponse.onCreated(PostConverter.toWritePostResultDTO(post));
    }

    //게시글 수정
    @PutMapping("/post/write")
    public ApiResponse<PostResponseDTO.WritePostResultDTO> updatePost(
            @RequestBody @Valid PostRequestDTO.WritePostDTO request, @RequestParam(name = "id") Long postId) {

        PostEntity post = postService.updatePost(request, postId);

        return ApiResponse.onSuccess(PostConverter.toWritePostResultDTO(post));
    }

    //게시글 좋아요 및 즐겨찾기
    @PostMapping("/post/reaction")
    public ApiResponse<PostResponseDTO.ReactPostResultDTO> reactPost(@RequestBody @Valid PostRequestDTO.ReactPostDTO request) {

        UserPostReactionEntity userPostReaction = postService.reactPost(request);

        return ApiResponse.onSuccess(PostConverter.toReactPostResultDTO(userPostReaction));
    }

    //게시글 댓글 작성
    @PostMapping("/post/comment/write")
    public ApiResponse<CommentResponseDTO.CreateCommentResultDTO> writeComment(
            @RequestBody @Valid CommentRequestDTO.CreateCommentDTO request) {

        CommentEntity comment = commentService.writeComment(request);

        return ApiResponse.onCreated(CommentConverter.toCreateCommentResultDTO(comment));
    }

    //댓글 좋아요
    @PostMapping("/post/comment/like")
    public ApiResponse<CommentResponseDTO.LikeCommentResultDTO> likeComment(
            @RequestBody @Valid CommentRequestDTO.LikeCommentDTO request) {

        CommentLikeEntity commentLike = commentService.likeComment(request);

        return ApiResponse.onSuccess(CommentConverter.toLikeCommentResultDTO(commentLike));
    }

    //신고
    @PostMapping("/report")
    public ApiResponse<ReportResponseDTO.ReportResultDTO> report(
            @RequestBody @Valid ReportRequestDTO.CreateReportDTO request) {

        ReportEntity report = reportService.createReport(request);

        return ApiResponse.onSuccess(ReportConverter.toReportResultDTO(report));
    }

    //게시글 이미지 업로드
    @PostMapping("/post/upload-image")
    public ApiResponse<PostImageResponseDTO.UploadPostImageResultDTO> uploadPostImage(
            @RequestParam(value = "file") MultipartFile multipartFile
            ) {

        PostImageResponseDTO.UploadPostImageResultDTO postImageResultDTO = postImageService.uploadPostImage(multipartFile);

        return ApiResponse.onSuccess(postImageResultDTO);
    }

}
