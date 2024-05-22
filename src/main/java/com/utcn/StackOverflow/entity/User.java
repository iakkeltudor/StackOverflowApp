package com.utcn.StackOverflow.entity;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name="user")
public class User {

    @Id
    @Column(name="U_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;

    @OneToMany(mappedBy = "ID")
    private Set<Question> questionSet;

    @OneToMany(mappedBy = "ID")
    private Set<Answer> answerSet;

    @Column(name="username")
    private String username;

    @Column(name="password")
    private String password;

    public User() {

    }

    public User(Long ID, String username, String password) {
        this.ID = ID;
        this.username = username;
        this.password = password;
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Question> getQuestionSet() {
        return questionSet;
    }

    public void setQuestionSet(Set<Question> questionSet) {
        this.questionSet = questionSet;
    }

    public Set<Answer> getAnswerSet() {
        return answerSet;
    }

    public void setAnswerSet(Set<Answer> answerSet) {
        this.answerSet = answerSet;
    }
}


