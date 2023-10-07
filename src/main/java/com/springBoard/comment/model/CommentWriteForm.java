package com.springBoard.comment.model;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

public class CommentWriteForm implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotBlank
    private String content;

    public CommentWriteForm() {
    }

    public CommentWriteForm(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "CommentWriteForm{" +
                "content='" + content + '\'' +
                '}';
    }
}
