package com.springBoard.config;

import com.springBoard.board.model.Board;
import com.springBoard.board.repository.BoardRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.util.List;

public class BoardUrlMapInit {
    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    private final BoardRepository boardRepository;

    public BoardUrlMapInit(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void init(){
        List<Board> boards = boardRepository.findAll();
        Board.initBoardUrlMap(boards);
    }
}
