package com.example.globalStudents.domain.search.dto;

import lombok.Builder;

@Builder
public class popularPostDTO {
    public String postId;
    public String boardName;
    public Integer rank;
    public String title;
    public Integer likes;
}
