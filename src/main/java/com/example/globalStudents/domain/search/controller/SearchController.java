package com.example.globalStudents.domain.search.controller;


import com.example.globalStudents.domain.chat.dto.*;
import com.example.globalStudents.domain.search.dto.PopularPostSearchResponse;
import com.example.globalStudents.domain.search.dto.TotalSearchResponse;
import com.example.globalStudents.domain.search.dto.UnivSearchResponse;
import com.example.globalStudents.domain.search.service.SearchService;
import com.example.globalStudents.global.apiPayload.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/search")
@Controller
public class SearchController {
    private final SearchService searchService;


    @GetMapping("/total")
    public ApiResponse<TotalSearchResponse> totalSearch(
            @RequestParam(name = "q") String keyword,
            @RequestParam(name = "boardId") String board_id,
            @RequestParam(name = "page") String page){
        TotalSearchResponse data = searchService.getTotalSearch(keyword, board_id, page);
        return ApiResponse.onSuccess(data);
    }

    @GetMapping("/university")
    public ApiResponse<UnivSearchResponse> universitySearch(@RequestParam(name = "q") String keyword){
        UnivSearchResponse data = searchService.getUniversitySearch(keyword);
        return ApiResponse.onSuccess(data);
    }

    @GetMapping("/popular-post")
    public ApiResponse<PopularPostSearchResponse> popularPostSearch(@RequestParam(name = "boardId") String board_id){
        PopularPostSearchResponse data = searchService.getPopularPostSearch(board_id);
        return ApiResponse.onSuccess(data);
    }

}
