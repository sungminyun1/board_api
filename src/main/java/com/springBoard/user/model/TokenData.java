package com.springBoard.user.model;

import java.io.Serializable;

public class TokenData implements Serializable {
    private static final long serialVersionUID = 1L;

    private String token;

    public TokenData(){}

    public TokenData(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
