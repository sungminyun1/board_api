package com.springBoard.config;

import com.springBoard.board.repository.BoardRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebMvcConfig {

    @Bean
    public BoardUrlMapInit boardUrlMapInit(BoardRepository boardRepository){
        return new BoardUrlMapInit(boardRepository);
    }
}
