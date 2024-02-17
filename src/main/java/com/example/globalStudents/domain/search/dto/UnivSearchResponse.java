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
public class UnivSearchResponse {
    public String q;    //검색어
    public Integer counts;
    public List<univDTO> searchResults;
}
