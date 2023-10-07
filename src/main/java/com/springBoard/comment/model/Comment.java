package com.springBoard.comment.model;

import java.io.Serializable;
import java.util.Date;

public class Comment implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String rid;
    private Long postId;
    private Long userId;
    private Date cDate;
    private Date uDate;
    private String text;

    public Comment(Long id, String rid, Long postId, Long userId, Date cDate, Date uDate, String text) {
        this.id = id;
        this.rid = rid;
        this.postId = postId;
        this.userId = userId;
        this.cDate = cDate;
        this.uDate = uDate;
        this.text = text;
    }

    public Long getId() {
        return id;
    }

    public String getRid() {
        return rid;
    }

    public Long getPostId() {
        return postId;
    }

    public Long getUserId() {
        return userId;
    }

    public Date getcDate() {
        return cDate;
    }

    public Date getuDate() {
        return uDate;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", rid='" + rid + '\'' +
                ", postId=" + postId +
                ", userId=" + userId +
                ", cDate=" + cDate +
                ", uDate=" + uDate +
                ", text='" + text + '\'' +
                '}';
    }

    public static class Builder{
        private Long id;
        private String rid;
        private Long postId;
        private Long userId;
        private Date cDate;
        private Date uDate;
        private String text;

        public Builder(){};

        public Builder id(Long id){
            this.id = id;
            return this;
        }

        public Builder rid(String rid){
            this.rid = rid;
            return this;
        }

        public Builder postId(Long postId){
            this.postId = postId;
            return this;
        }

        public Builder userId(Long userId){
            this.userId = userId;
            return this;
        }

        public Builder cDate(Date cDate){
            this.cDate = cDate;
            return this;
        }

        public Builder uDate(Date uDate){
            this.uDate = uDate;
            return this;
        }

        public Builder text(String text){
            this.text = text;
            return this;
        }

        public Comment build(){
            return new Comment(id, rid, postId, userId, cDate, uDate, text);
        }
    }
}
