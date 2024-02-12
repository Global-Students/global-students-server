package com.example.globalStudents.domain.board.service;

import com.example.globalStudents.domain.board.converter.BoardConverter;
import com.example.globalStudents.domain.board.dto.BoardResponseDTO;
import com.example.globalStudents.domain.board.entity.BoardEntity;
import com.example.globalStudents.domain.board.entity.PostEntity;
import com.example.globalStudents.domain.board.repository.BoardRepository;
import com.example.globalStudents.domain.board.repository.PostRepository;
import com.example.globalStudents.global.apiPayload.code.status.ErrorStatus;
import com.example.globalStudents.global.apiPayload.exception.handler.ExceptionHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional
public class BoardServiceImpl implements BoardService{

    private final BoardRepository boardRepository;
    private final PostRepository postRepository;

    @Override
    public BoardResponseDTO.BoardResultDTO getBoardHome(Long boardId, String sortingType, int page, String keyword) {
        BoardEntity board = boardRepository.findById(boardId).orElseThrow(()->new ExceptionHandler(ErrorStatus.BOARD_BOARD_ID_INVALID));

        if (page < 0)
            throw new ExceptionHandler(ErrorStatus._BAD_REQUEST);
        else if (page > 0)
            page--;


        String sortingProperty = (sortingType.equals("popular")) ? "likes" : "createdAt";
        PageRequest pageRequest = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, sortingProperty));

        Page<PostEntity> postList;
        //검색어 조회
        if (keyword == null || keyword.isBlank()) {
            postList = postRepository.findAllByBoard(board, pageRequest);
        }
        //기본 조회
        else {
            postList = postRepository.findAllSearch(board, keyword, pageRequest);
        }

        if (postList.getTotalElements() == 0) {
            throw new ExceptionHandler(ErrorStatus.POST_NOT_FOUND);
        }
        else if (page >= postList.getTotalPages()) {
            throw new ExceptionHandler(ErrorStatus._BAD_REQUEST);
        }

        BoardResponseDTO.PageInfoDTO pageInfo = BoardResponseDTO.PageInfoDTO.builder()
                .page(++page)
                .size(10)
                .totalPage(postList.getTotalPages())
                .totalPost((int) postList.getTotalElements())
                .build();

        // 공지 게시판 ID (임의 설정)
        Long noticeBoardId = 1L;
        BoardEntity noticeBoard = boardRepository.findById(noticeBoardId).orElseThrow(()->new RuntimeException("공지 게시판 NOT_FOUND"));

        PostEntity noticePost = postRepository.findFirstByBoardOrderByCreatedAtDesc(board);

        return BoardResponseDTO.BoardResultDTO.builder()
                .pageInfo(pageInfo)
                .noticePost(BoardConverter.toNoticePostDTO(noticePost))
                .popular(BoardConverter.toPopularPostDTOList(getPopularPostList(board)))
                .posts(BoardConverter.toPostDTOList(postList))
                .build();
    }

    public Page<PostEntity> getPopularPostList(BoardEntity board) {
        PageRequest pageRequest = PageRequest.of(0, 5, Sort.by(Sort.Direction.DESC, "createdAt"));

        return postRepository.findAllPopularPost(board, 1, pageRequest);
    }

}
