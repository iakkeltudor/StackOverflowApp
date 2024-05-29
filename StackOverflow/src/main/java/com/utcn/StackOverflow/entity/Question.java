package com.utcn.StackOverflow.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="question")
public class Question {

    @Id
    @Column(name="Q_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;

    @OneToMany(mappedBy = "questionId")
    List<Answer> answerSet;

    @ManyToOne
    @JoinColumn(name="author_id", referencedColumnName = "U_ID")
    private User author;

    @Column(name="title")
    private String title;

    @Column(name="text")
    private String text;

    @Column(name="creation_date_time")
    private LocalDateTime creationDateTime;

    @ManyToMany
    @JoinTable(
            name = "question_tag",
            joinColumns = @JoinColumn(name = "q_id"),
            inverseJoinColumns = @JoinColumn(name = "t_id")
    )
    private List<Tag> tags;

    @Column(name="image_path")
    private String imagePath;

    @Column(name="score")
    private int score;

    public Question() {

    }

    public Question(User user, String title, String text, LocalDateTime creationDateTime, List<Tag> tags, String imagePath) {
        this.author = user;
        this.title = title;
        this.text = text;
        this.creationDateTime = creationDateTime;
        this.tags = tags;
        this.imagePath = imagePath;
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public List<Answer> getAnswerSet() {
        return answerSet;
    }

    public void setAnswerSet(List<Answer> answerSet) {
        this.answerSet = answerSet;
    }

//    public Long getAuthorId() {
//        return authorId;
//    }
//
//    public void setAuthorId(Long authorId) {
//        this.authorId = authorId;
//    }

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

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
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

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
