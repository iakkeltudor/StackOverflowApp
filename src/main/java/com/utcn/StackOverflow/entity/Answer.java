package com.utcn.StackOverflow.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name="answer")
public class Answer {
    @Id
    @Column(name="A_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;

    @Column(name = "author_id")
    private Long authorId;

    @Column(name = "text")
    private String text;

    @Column(name="creation_date_time")
    private LocalDateTime creationDateTime;

    @Column(name = "question_id")
    private Long questionId;

    public Answer() {

    }

    public Answer(Long authorId, String text, LocalDateTime creationDateTime, Long questionId) {
        this.authorId = authorId;
        this.text = text;
        this.creationDateTime = creationDateTime;
        this.questionId = questionId;
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
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

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }
}
