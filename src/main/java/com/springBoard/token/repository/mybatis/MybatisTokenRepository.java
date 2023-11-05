package com.springBoard.token.repository.mybatis;

import com.springBoard.token.model.Token;
import com.springBoard.token.repository.TokenRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class MybatisTokenRepository implements TokenRepository {
    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    private final TokenMapper tokenMapper;

    public MybatisTokenRepository(TokenMapper tokenMapper) {
        this.tokenMapper = tokenMapper;
    }

    @Override
    public void save(Token token) {
        tokenMapper.save(token);
    }

    @Override
    public Optional<Token> findByAT(String accessToken) {
        return tokenMapper.findByAT(accessToken);
    }

    @Override
    public void updateByAT(Token token) {
        tokenMapper.updateByAT(token);
    }

    @Override
    public void deleteByAT(String accessToken) {
        tokenMapper.deleteByAT(accessToken);
    }
}
