package com.utcn.StackOverflow.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="answer")
public class Answer {
    @Id
    @Column(name="A_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;

    @ManyToOne
    @JoinColumn(name="author_id", referencedColumnName = "U_ID")
    private User author;

    @Column(name = "text")
    private String text;

    @Column(name="creation_date_time")
    private LocalDateTime creationDateTime;

    @Column(name = "question_id")
    private Long questionId;

    @Column(name = "image_path")
    private String imagePath;

    @Column(name = "score")
    private int score;

    public Answer() {

    }

    public Answer(User author, String text, LocalDateTime creationDateTime, Long questionId, String imagePath) {
        this.author = author;
        this.text = text;
        this.creationDateTime = creationDateTime;
        this.questionId = questionId;
        this.imagePath = imagePath;
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }


    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
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

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
