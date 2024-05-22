package com.utcn.StackOverflow.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.utcn.StackOverflow.entity.Tag;
import com.utcn.StackOverflow.entity.User;

import java.time.LocalDateTime;
import java.util.List;

public class InsertQuestionRequest {
    private Long authorId;
    private String title;
    private String text;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime creationDateTime;
    private List<Tag> tags;
    private String imagePath;

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getCreationDateTime() {
        return creationDateTime;
    }
    public void setCreationDateTime(LocalDateTime creationDateTime) {
        this.creationDateTime = creationDateTime;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
