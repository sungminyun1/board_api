package com.springBoard.token.repository;

import com.springBoard.token.model.Token;

import java.util.Optional;

public interface TokenRepository {
    void save(Token token);

    Optional<Token> findByAT(String accessToken);

    void updateByAT(Token token);

    void deleteByAT(String accessToken);
}
