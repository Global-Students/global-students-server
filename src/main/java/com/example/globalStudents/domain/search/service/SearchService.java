package com.example.globalStudents.domain.search.service;

import com.example.globalStudents.domain.board.entity.BoardEntity;
import com.example.globalStudents.domain.board.entity.PostEntity;
import com.example.globalStudents.domain.board.repository.BoardRepository;
import com.example.globalStudents.domain.board.repository.PostRepository;
import com.example.globalStudents.domain.search.converter.SearchConverter;
import com.example.globalStudents.domain.search.dto.PopularPostSearchResponse;
import com.example.globalStudents.domain.search.dto.TotalSearchResponse;
import com.example.globalStudents.domain.search.dto.UnivSearchResponse;
import com.example.globalStudents.domain.user.entity.UniversityEntity;
import com.example.globalStudents.domain.user.repository.UniversityRepository;
import com.example.globalStudents.global.apiPayload.code.status.ErrorStatus;
import com.example.globalStudents.global.apiPayload.exception.handler.ExceptionHandler;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class SearchService {
    private final SearchConverter searchConverter;
    private final PostRepository postRepository;
    private final BoardRepository boardRepository;
    private final UniversityRepository universityRepository;

    public TotalSearchResponse getTotalSearch(String keyword, String boardId, String page) {
        BoardEntity board = boardRepository.findById(Long.parseLong(boardId)).orElseThrow(()->new ExceptionHandler(ErrorStatus.BOARD_BOARD_ID_INVALID));

        if(board == null){
            throw new ExceptionHandler(ErrorStatus.BOARD_BOARD_ID_INVALID);
        }

        String boardName = board.getName();
        Integer pageNum = Integer.parseInt(page) - 1;
        PageRequest pageRequest = PageRequest.of(pageNum, 10, Sort.by(Sort.Direction.DESC, "createdAt"));

        Page<PostEntity> postList = postRepository.findAllSearch(board, keyword, pageRequest);

        if (postList.getTotalElements() == 0) {
            throw new ExceptionHandler(ErrorStatus.SEARCH_TOTAL_ISEMPTY);
        }
        else if (pageNum >= postList.getTotalPages()) {
            throw new ExceptionHandler(ErrorStatus._BAD_REQUEST);
        }

        TotalSearchResponse response = searchConverter.toTotalSearchResponse(keyword, pageNum, postList);
        return response;
    }

    public PopularPostSearchResponse getPopularPostSearch(String boardId) {
        BoardEntity board = boardRepository.findById(Long.parseLong(boardId)).orElseThrow(()->new ExceptionHandler(ErrorStatus.BOARD_BOARD_ID_INVALID));
        if(board == null){
            throw new ExceptionHandler(ErrorStatus.BOARD_BOARD_ID_INVALID);
        }

        String boardName = board.getName();
        PageRequest pageRequest = PageRequest.of(0, 5);

        LocalDateTime minday = LocalDateTime.now().minusDays(7);
        List<PostEntity> popularPostList = postRepository.findTopPopularPosts(board, minday,pageRequest);

        PopularPostSearchResponse response = searchConverter.toPopularPostResponse(popularPostList, boardName);
        return response;
    }

    public UnivSearchResponse getUniversitySearch(String keyword) {
        List<UniversityEntity> universityList = universityRepository.findAllSearch(keyword);

        if(universityList.isEmpty()){
            throw new ExceptionHandler(ErrorStatus.SEARCH_UNIVERSITY_ISEMPTY);
        }

        UnivSearchResponse response = searchConverter.toUniversityResponse(keyword, universityList);
        return response;
    }
}
