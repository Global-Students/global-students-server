package com.example.globalStudents.domain.board.service;

import com.example.globalStudents.domain.board.dto.PostRequestDTO;
import com.example.globalStudents.domain.board.entity.PostEntity;
import com.example.globalStudents.domain.board.entity.UserPostReactionEntity;

public interface PostService {

    public PostEntity writePost(PostRequestDTO.WritePostDTO request);

    public PostEntity updatePost(PostRequestDTO.WritePostDTO request, Long postId);

    public UserPostReactionEntity reactPost(PostRequestDTO.ReactPostDTO request);

    public PostEntity getPost(Long postId);

}
