package com.springBoard.token.repository.mybatis;

import com.springBoard.token.model.Token;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;

@Mapper
public interface TokenMapper {
    void save(Token token);

    Optional<Token> findByAT(String accessToken);

    void updateByAT(Token token);

    void deleteByAT(@Param("access_token") String accessToken);
}
