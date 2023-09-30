package com.springBoard.post.model;

import org.springframework.lang.Nullable;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

public class PostWriteForm implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    public PostWriteForm(String title, String content) {
        this.title = title;
        this.content = content;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "PostWriteForm{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
