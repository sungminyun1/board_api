package com.springBoard.user.model;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String userId;
    private String password;
    private String userName;
    private Date cDate;
    private Date lastLogin;
    private String hostIp;
    private Integer isUser;

    public User(){}

    public User(Long id, String userId, String password, String userName, Date cDate, Date lastLogin, String hostIp, Integer isUser) {
        this.id = id;
        this.userId = userId;
        this.password = password;
        this.userName = userName;
        this.cDate = cDate;
        this.lastLogin = lastLogin;
        this.hostIp = hostIp;
        this.isUser = isUser;
    }

    public Long getId() {
        return id;
    }

//    public void setId(Long id) {
//        this.id = id;
//    }

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

    public String getHostIp() {
        return hostIp;
    }

    public void setHostIp(String hostIp) {
        this.hostIp = hostIp;
    }

    public Integer getisUser() {
        return isUser;
    }

    public void setisUser(Integer isUser) {
        this.isUser = isUser;
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
                ", hostIp='" + hostIp + '\'' +
                ", isUser=" + isUser +
                '}';
    }

    static public class Builder {
        private Long id;
        private String userId;
        private String password;
        private String userName;
        private Date cDate;
        private Date lastLogin;
        private String hostIp;
        private Integer isUser;

        public Builder(){};

        public Builder id(Long id){
            this.id = id;
            return this;
        }

        public Builder userId(String userId){
            this.userId = userId;
            return this;
        }

        public Builder password(String password){
            this.password = password;
            return this;
        }

        public Builder userName(String userName) {
            this.userName = userName;
            return this;
        }

        public Builder hostIp(String hostIP){
            this.hostIp = hostIP;
            return this;
        }

        public Builder isUser(Integer isUser){
            this.isUser = isUser;
            return this;
        }

        public User build(){
            return new User(id,userId,password,userName,cDate,lastLogin,hostIp,isUser);
        }
    }
}
