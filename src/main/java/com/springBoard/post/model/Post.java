package com.springBoard.post.model;

import java.io.Serializable;
import java.util.Date;

public class Post implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String rid;
    private Long boardId;
    private Long userId;
    private Long commentCount;
    private Long readCount;
    private Date cDate;
    private Date uDate;
    private String title;
    private String text;

    public Post(){}

    public Post(Long id, String rid, Long boardId, Long userId, Long commentCount, Long readCount, Date cDate, Date uDate, String title, String text) {
        this.id = id;
        this.rid = rid;
        this.boardId = boardId;
        this.userId = userId;
        this.commentCount = commentCount;
        this.readCount = readCount;
        this.cDate = cDate;
        this.uDate = uDate;
        this.title = title;
        this.text = text;
    }

    public Long getId() {
        return id;
    }

    public String getRid() {
        return rid;
    }

    public Long getBoardId() {
        return boardId;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getCommentCount() {
        return commentCount;
    }

    public Long getReadCount() {
        return readCount;
    }

    public Date getcDate() {
        return cDate;
    }

    public Date getuDate() {
        return uDate;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public void setCommentCount(Long commentCount) {
        this.commentCount = commentCount;
    }

    public void setReadCount(Long readCount) {
        this.readCount = readCount;
    }

    public void setcDate(Date cDate) {
        this.cDate = cDate;
    }

    public void setuDate(Date uDate) {
        this.uDate = uDate;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", rid='" + rid + '\'' +
                ", boardId=" + boardId +
                ", userId=" + userId +
                ", commentCount=" + commentCount +
                ", readCount=" + readCount +
                ", cDate=" + cDate +
                ", uDate=" + uDate +
                ", title='" + title + '\'' +
                ", text='" + text + '\'' +
                '}';
    }

    public static class Builder {
        private Long id;
        private String rid;
        private Long boardId;
        private Long userId;
        private Long commentCount;
        private Long readCount;
        private Date cDate;
        private Date uDate;
        private String title;
        private String text;

        public Builder(){};

        public Post build(){
            return new Post(id, rid, boardId, userId, commentCount, readCount,
                    cDate, uDate, title, text);
        }

        public Builder id(Long id){
            this.id = id;
            return this;
        }

        public Builder rid(String rid){
            this.rid = rid;
            return this;
        }

        public Builder boardId(Long boardId){
            this.boardId = boardId;
            return this;
        }

        public Builder userId(Long userId){
            this.userId = userId;
            return this;
        }

        public Builder commentCount(Long commentCount){
            this.commentCount = commentCount;
            return this;
        }

        public Builder readCount(Long readCount){
            this.readCount = readCount;
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

        public Builder title(String title){
            this.title = title;
            return this;
        }

        public Builder text(String text){
            this.text = text;
            return this;
        }
    }
}
