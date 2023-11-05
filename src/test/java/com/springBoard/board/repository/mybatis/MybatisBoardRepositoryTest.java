package com.springBoard.board.repository.mybatis;

import com.springBoard.board.model.Board;
import com.springBoard.board.repository.BoardRepository;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MybatisBoardRepositoryTest {
    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    @Autowired
    BoardRepository boardRepository;

    @Test
    public void findAll(){
        List<Board> boards = boardRepository.findAll();
        for(Board b : boards){
            log.info( b.toString());
        }
    }
}