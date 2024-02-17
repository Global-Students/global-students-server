package com.example.globalStudents.domain.search.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TotalSearchResponse {
    public String q;
    public Integer page;
    public Integer size;
    public Integer totalPage;
    public Integer totalPost;
    public List<totalPostDTO> posts;

}
