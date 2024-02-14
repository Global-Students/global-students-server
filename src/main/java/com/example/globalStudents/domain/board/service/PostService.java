package com.example.globalStudents.domain.board.service;

import com.example.globalStudents.domain.board.dto.PostRequestDTO;
import com.example.globalStudents.domain.board.dto.PostResponseDTO;

public interface PostService {

    public PostResponseDTO.WritePostResultDTO writePost(PostRequestDTO.WritePostDTO request);

    public PostResponseDTO.WritePostResultDTO updatePost(PostRequestDTO.WritePostDTO request, Long postId);

    public PostResponseDTO.ReactPostResultDTO reactPost(PostRequestDTO.ReactPostDTO request);

    public PostResponseDTO.GetPostResultDTO getPost(Long postId);

}
