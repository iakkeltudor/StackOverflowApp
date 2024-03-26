package com.utcn.StackOverflow.entity;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name="question")
public class Question {

    @Id
    @Column(name="Q_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;

    @OneToMany(mappedBy = "questionId")
    Set<Answer> answerSet;

    @ManyToMany(mappedBy = "questionSet")
    Set<User> userSet;

    @Column(name="author_id")
    private Long authorId;

    @Column(name="title")
    private String title;

    @Column(name="text")
    private String text;

    @Column(name="creation_date")
    private String date;

    @Column(name="creation_time")
    private String time;

    public Question() {

    }

    public Question(Long ID, Long authorId, String title, String text, String date, String time) {
        this.ID = ID;
        this.authorId = authorId;
        this.title = title;
        this.text = text;
        this.date = date;
        this.time = time;
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

    public Set<Answer> getAnswerSet() {
        return answerSet;
    }

    public void setAnswerSet(Set<Answer> answerSet) {
        this.answerSet = answerSet;
    }

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
}
