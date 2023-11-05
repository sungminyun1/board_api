package com.springBoard.user.model;

public class UserSearchCond {
    private Long id;
    private String userId;
    private String userName;
    private String hostIp;
    private Integer isUser;

    public UserSearchCond() {
    }

    public UserSearchCond(Long id, String userId, String userName, String hostIp, Integer isUser) {
        this.id = id;
        this.userId = userId;
        this.userName = userName;
        this.hostIp = hostIp;
        this.isUser = isUser;
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

    public String getHostIp() {
        return hostIp;
    }

    public void setHostIp(String hostIp) {
        this.hostIp = hostIp;
    }

    public Integer getIsUser() {
        return isUser;
    }

    public void setIsUser(Integer isUser) {
        this.isUser = isUser;
    }

    public static class Builder {
        private Long id;
        private String userId;
        private String userName;
        private String hostIp;
        private Integer isUser;

        public Builder id(Long id){
            this.id = id;
            return this;
        }

        public Builder userId(String userId){
            this.userId = userId;
            return this;
        }

        public Builder userName(String userName){
            this.userName = userName;
            return this;
        }
        public Builder hostIp(String hostIp){
            this.hostIp = hostIp;
            return this;
        }
        public Builder isUser(Integer isUser){
            this.isUser = isUser;
            return this;
        }
        public UserSearchCond build(){
            return new UserSearchCond(id, userId, userName, hostIp, isUser);
        }
    }
}
