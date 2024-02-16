package com.example.globalStudents.domain.board.service;

import com.example.globalStudents.domain.board.dto.PostRequestDTO;
import com.example.globalStudents.domain.board.dto.PostResponseDTO;

public interface PostService {

    public PostResponseDTO.WritePostResultDTO writePost(PostRequestDTO.WritePostDTO request, String userId);

    public PostResponseDTO.WritePostResultDTO updatePost(PostRequestDTO.WritePostDTO request, Long postId, String userId);

    public PostResponseDTO.ReactPostResultDTO reactPost(PostRequestDTO.ReactPostDTO request, String userId);

    public PostResponseDTO.GetPostResultDTO getPost(Long postId);

}
