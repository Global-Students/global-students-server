package com.example.globalStudents.domain.board.controller;

import com.example.globalStudents.domain.board.dto.*;
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

        BoardResponseDTO.BoardResultDTO boardResultDTO = boardService.getBoardHome(boardId, sortingType, page, keyword);

        return ApiResponse.onSuccess(boardResultDTO);
    }

    //게시글 조회
    @GetMapping("/{board_id}/posts/{post_id}")
    public ApiResponse<PostResponseDTO.GetPostResultDTO> getPost(
            @PathVariable("board_id") Long boardId, @PathVariable("post_id") Long postId) {

        PostResponseDTO.GetPostResultDTO getPostResultDTO = postService.getPost(postId);

        return ApiResponse.onSuccess(getPostResultDTO);

    }

    //게시글 작성
    @PostMapping("/post/write")
    public ApiResponse<PostResponseDTO.WritePostResultDTO> writePost(
            @RequestBody @Valid PostRequestDTO.WritePostDTO request) {

        PostResponseDTO.WritePostResultDTO writePostResultDTO = postService.writePost(request);

        return ApiResponse.onCreated(writePostResultDTO);
    }

    //게시글 수정
    @PutMapping("/post/write")
    public ApiResponse<PostResponseDTO.WritePostResultDTO> updatePost(
            @RequestBody @Valid PostRequestDTO.WritePostDTO request, @RequestParam(name = "id") Long postId) {

        PostResponseDTO.WritePostResultDTO writePostResultDTO = postService.updatePost(request, postId);

        return ApiResponse.onSuccess(writePostResultDTO);
    }

    //게시글 좋아요 및 즐겨찾기
    @PostMapping("/post/reaction")
    public ApiResponse<PostResponseDTO.ReactPostResultDTO> reactPost(@RequestBody @Valid PostRequestDTO.ReactPostDTO request) {

        PostResponseDTO.ReactPostResultDTO reactPostResultDTO = postService.reactPost(request);

        return ApiResponse.onSuccess(reactPostResultDTO);
    }

    //게시글 댓글 작성
    @PostMapping("/post/comment/write")
    public ApiResponse<CommentResponseDTO.CreateCommentResultDTO> writeComment(
            @RequestBody @Valid CommentRequestDTO.CreateCommentDTO request) {

        CommentResponseDTO.CreateCommentResultDTO createCommentResultDTO = commentService.writeComment(request);

        return ApiResponse.onCreated(createCommentResultDTO);
    }

    //댓글 좋아요
    @PostMapping("/post/comment/like")
    public ApiResponse<CommentResponseDTO.LikeCommentResultDTO> likeComment(
            @RequestBody @Valid CommentRequestDTO.LikeCommentDTO request) {

        CommentResponseDTO.LikeCommentResultDTO likeCommentResultDTO = commentService.likeComment(request);

        return ApiResponse.onSuccess(likeCommentResultDTO);
    }

    //신고
    @PostMapping("/report")
    public ApiResponse<ReportResponseDTO.ReportResultDTO> report(
            @RequestBody @Valid ReportRequestDTO.CreateReportDTO request) {

        ReportResponseDTO.ReportResultDTO reportResultDTO = reportService.createReport(request);

        return ApiResponse.onSuccess(reportResultDTO);
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
