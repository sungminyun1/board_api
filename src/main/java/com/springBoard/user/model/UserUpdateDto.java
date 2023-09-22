package com.springBoard.user.model;

import java.util.Date;

public class UserUpdateDto {
    private String password;
    private String userName;
    private Date lastLogin;

    public UserUpdateDto() {
    }

    public UserUpdateDto(String password, String userName, Date lastLogin) {
        this.password = password;
        this.userName = userName;
        this.lastLogin = lastLogin;
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

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }
}
