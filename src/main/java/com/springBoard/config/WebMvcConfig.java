package com.springBoard.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springBoard.aop.LoginCheck;
import com.springBoard.aop.LoginCheckAspect;
import com.springBoard.board.repository.BoardRepository;
import com.springBoard.interceptor.LoginCheckInterceptor;
import com.springBoard.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Bean
    public BoardUrlMapInit boardUrlMapInit(BoardRepository boardRepository){
        return new BoardUrlMapInit(boardRepository);
    }

    @Bean
    public LoginCheckAspect loginCheckAspect(){
        return new LoginCheckAspect();}

//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new LoginCheckInterceptor(userRepository, objectMapper))
//                .addPathPatterns("/board/**");
//    }
}
