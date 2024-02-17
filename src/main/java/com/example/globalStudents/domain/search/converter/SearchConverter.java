package com.example.globalStudents.domain.search.converter;

import com.example.globalStudents.domain.board.entity.BoardEntity;
import com.example.globalStudents.domain.board.entity.PostEntity;
import com.example.globalStudents.domain.board.repository.BoardRepository;
import com.example.globalStudents.domain.search.dto.*;
import com.example.globalStudents.domain.user.entity.UniversityEntity;
import jakarta.persistence.Converter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SearchConverter {
    private final BoardRepository boardRepository;

    public PopularPostSearchResponse toPopularPostResponse(List<PostEntity> popularPostList, String boardName) {
        List<popularPostDTO> posts = new ArrayList<>();
        Integer i = 0;
        for(PostEntity post : popularPostList){
            i++;
            popularPostDTO dto = popularPostDTO.builder()
                    .postId(post.getId().toString())
                    .boardName(boardName)
                    .rank(i)
                    .title(post.getTitle())
                    .likes(post.getLikes())
                    .build();
            posts.add(dto);
        }
        return PopularPostSearchResponse.builder()
                .posts(posts)
                .build();
    }

    public TotalSearchResponse toTotalSearchResponse(String q, Integer page, Page<PostEntity> postList) {
        List<totalPostDTO> posts = postList.stream().map(SearchConverter::totalPostDTO).collect(Collectors.toList());

        return TotalSearchResponse.builder()
                .q(q)
                .page(++page)
                .size(10)
                .totalPost(posts.size())
                .totalPage(postList.getTotalPages())
                .posts(posts)
                .build();
    }
    public static totalPostDTO totalPostDTO(PostEntity post){
        return totalPostDTO.builder()
                .postId(post.getTitle())
                .title(post.getTitle())
                .views(post.getView())
                .author(post.getUser().getNickname())
                .comments((int)post.getCommentList().stream().count())
                .date(post.getCreatedAt().toString())
                .likes(post.getLikes())
                .build();
    }

    public UnivSearchResponse toUniversityResponse(String q,List<UniversityEntity> universityList) {
        List<univDTO> univList = new ArrayList<>();
        for(UniversityEntity univ : universityList){
            Long univ_id = univ.getId();
            BoardEntity board = boardRepository.findByUniversityIdAndNameContaining(univ_id, "All Students");
            univDTO dto = univDTO.builder()
                    .univId(univ.getId().toString())
                    .univName(univ.getName())
                    .univBoardId(board.getId().toString())
                    .build();
            univList.add(dto);
        }
        return UnivSearchResponse.builder()
                .q(q)
                .counts(universityList.size())
                .searchResults(univList)
                .build();

    }
}
