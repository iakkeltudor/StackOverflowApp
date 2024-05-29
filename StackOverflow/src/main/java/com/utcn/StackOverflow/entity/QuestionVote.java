package com.utcn.StackOverflow.entity;

import jakarta.persistence.*;

@Entity
@Table(name="question_votes")
public class QuestionVote {
    @Id
    @Column(name="QV_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;

    @ManyToOne
    @JoinColumn(name="q_id", referencedColumnName = "Q_ID")
    private Question question;

    @ManyToOne
    @JoinColumn(name="u_id", referencedColumnName = "U_ID")
    private User user;

    @ManyToOne
    @JoinColumn(name="a_id", referencedColumnName = "A_ID")
    private Answer answer;

    @Column(name="vote")
    private String vote;

    public QuestionVote() {
    }

    public QuestionVote(Question question, User user, String vote) {
        this.question = question;
        this.user = user;
        this.vote = vote;
    }

    public QuestionVote(Answer answer, User user, String vote) {
        this.answer = answer;
        this.user = user;
        this.vote = vote;
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getVote() {
        return vote;
    }

    public void setVote(String vote) {
        this.vote = vote;
    }

    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }
}
