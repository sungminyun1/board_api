package com.springBoard.board.repository.mybatis;

import com.springBoard.board.model.Board;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {
    List<Board> findAll();
}
