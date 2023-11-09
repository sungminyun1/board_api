package com.springBoard.token.model;

import java.io.Serializable;
import java.util.Date;

public class Token implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final String AT_HEADER = "ACCESS_TOKEN";
    public static final String RT_HEADER = "REFRESH_TOKEN";

    private Long userId;
    private String accessToken;
    private Date accessTokenExpire;
    private String refreshToken;
    private Date refreshTokenExpire;

    public Token(){}

    public Token(Long userId, String accessToken, Date accessTokenExpire, String refreshToken, Date refreshTokenExpire) {
        this.userId = userId;
        this.accessToken = accessToken;
        this.accessTokenExpire = accessTokenExpire;
        this.refreshToken = refreshToken;
        this.refreshTokenExpire = refreshTokenExpire;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Date getAccessTokenExpire() {
        return accessTokenExpire;
    }

    public void setAccessTokenExpire(Date accessTokenExpire) {
        this.accessTokenExpire = accessTokenExpire;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Date getRefreshTokenExpire() {
        return refreshTokenExpire;
    }

    public void setRefreshTokenExpire(Date refreshTokenExpire) {
        this.refreshTokenExpire = refreshTokenExpire;
    }

    public static class Builder {
        private Long userId;
        private String accessToken;
        private Date accessTokenExpire;
        private String refreshToken;
        private Date refreshTokenExpire;

        public Builder(){}

        public Token build(){
            return new Token(userId, accessToken, accessTokenExpire, refreshToken, refreshTokenExpire);
        }

        public Builder userId(Long userId) {
            this.userId = userId;
            return this;
        }

        public Builder accessToken(String accessToken) {
            this.accessToken = accessToken;
            return this;
        }

        public Builder accessTokenExpire(Date accessTokenExpire) {
            this.accessTokenExpire = accessTokenExpire;
            return this;
        }

        public Builder refreshToken(String refreshToken) {
            this.refreshToken = refreshToken;
            return this;
        }

        public Builder refreshTokenExpire(Date refreshTokenExpire) {
            this.refreshTokenExpire = refreshTokenExpire;
            return this;
        }
    }
}
