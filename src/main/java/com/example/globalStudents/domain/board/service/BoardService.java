package com.example.globalStudents.domain.board.service;

import com.example.globalStudents.domain.board.dto.BoardResponseDTO;

public interface BoardService {

    public BoardResponseDTO.BoardResultDTO getBoardHome(Long boardId, String sortingType, int page, String keyword);

}
