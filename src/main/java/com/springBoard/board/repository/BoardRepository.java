package com.springBoard.board.repository;

import com.springBoard.board.model.Board;

import java.util.List;

public interface BoardRepository {
    List<Board> findAll();
}
