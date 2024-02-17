package com.example.globalStudents.domain.search.dto;

import lombok.Builder;

@Builder
public class totalPostDTO {
    public String postId;
    public String title;
    public Integer comments;
    public String author;
    public String date;
    public Integer likes;
    public Integer views;
}
