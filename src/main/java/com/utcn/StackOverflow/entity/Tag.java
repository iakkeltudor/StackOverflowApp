package com.utcn.StackOverflow.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="tag")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="T_ID")
    private Long tagId;

    @Column(name="name")
    private String name;

    public Tag(String name)  {
        this.name = name;
    }

    public Tag() {}

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public List<Question> getQuestions() {
//        return questions;
//    }
//
//    public void setQuestions(List<Question> questions) {
//        this.questions = questions;
//   }


}
