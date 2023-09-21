package com.springBoard.user.model;

public class UserSearchCond {
    private Long id;

    private String userId;

    private String userName;

    public UserSearchCond() {
    }

    public UserSearchCond(Long id, String userId, String userName) {
        this.id = id;
        this.userId = userId;
        this.userName = userName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
