package com.springBoard.user.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class UserLoginForm implements Serializable {
    private static final long serialVersionUID = 1L;

    @Email
    @NotNull
    private String userId;

    @NotBlank
    private String password;

    public UserLoginForm() {
    }

    public UserLoginForm(String userId, String password) {
        this.userId = userId;
        this.password = password;
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
}
