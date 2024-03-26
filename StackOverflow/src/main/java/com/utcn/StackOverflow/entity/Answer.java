package com.utcn.StackOverflow.entity;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name="answer")
public class Answer {
    @Id
    @Column(name="A_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;

    @ManyToMany
    @JoinTable (
            name = "au",
            joinColumns = @JoinColumn(name = "FFF_A_ID"),
            inverseJoinColumns = @JoinColumn(name = "FFF_U_ID")
    )
    Set<User> userSet;

    @Column(name = "author")
    private String author;

    @Column(name = "text")
    private String text;

    @Column(name = "creation_date")
    private String date;

    @Column(name = "creation_time")
    private String time;

    @Column(name = "question_id")
    private Long questionId;

    public Answer() {

    }

    public Answer(Long ID, String author, String text, String date, String time, Long questionId) {
        this.ID = ID;
        this.author = author;
        this.text = text;
        this.date = date;
        this.time = time;
        this.questionId = questionId;
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public Set<User> getUserSet() {
        return userSet;
    }

    public void setUserSet(Set<User> userSet) {
        this.userSet = userSet;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }
}
