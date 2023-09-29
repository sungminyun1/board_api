package com.springBoard.board.repository.mybatis;

import com.springBoard.board.model.Board;
import com.springBoard.board.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MybatisBoardRepository implements BoardRepository {

    private final BoardMapper boardMapper;

    public MybatisBoardRepository(BoardMapper boardMapper) {
        this.boardMapper = boardMapper;
    }

    @Override
    public List<Board> findAll() {
        return boardMapper.findAll();
    }
}
