package com.springBoard.user.model;

import java.util.Date;

public class User {
    private Long id;

    private String userId;

    private String password;

    private String userName;

    private Date cDate;

    private Date lastLogin;

    public User(){}

    public User(Long id, String userId, String password, String userName, Date cDate, Date lastLogin) {
        this.id = id;
        this.userId = userId;
        this.password = password;
        this.userName = userName;
        this.cDate = cDate;
        this.lastLogin = lastLogin;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getcDate() {
        return cDate;
    }

    public void setcDate(Date cDate) {
        this.cDate = cDate;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", password='" + password + '\'' +
                ", userName='" + userName + '\'' +
                ", cDate=" + cDate +
                ", lastLogin=" + lastLogin +
                '}';
    }
}
