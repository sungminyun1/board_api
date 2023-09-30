package com.springBoard.post.model;

public class PostSearchCond {
    private Integer limit;
    private Integer offset;
    private Long boardId;
    private String rid;

    public PostSearchCond(Integer limit, Integer offset, Long boardId, String rid) {
        this.limit = limit;
        this.offset = offset;
        this.boardId = boardId;
        this.rid = rid;
    }

    public static class Builder {
        private Integer limit;
        private Integer offset;
        private Long boardId;
        private String rid;

        public Builder(){};

        public Builder rid(String rid) {
            this.rid = rid;
            return this;
        }

        public Builder limit(Integer limit){
            this.limit = limit;
            return this;
        }

        public Builder offset(Integer offset){
            this.offset = offset;
            return this;
        }

        public Builder boardId(Long boardId){
            this.boardId = boardId;
            return this;
        }

        public PostSearchCond build(){
            return new PostSearchCond(limit, offset, boardId, rid);}
    }
}
