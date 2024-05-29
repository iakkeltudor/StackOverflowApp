package com.utcn.StackOverflow.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.springframework.context.annotation.Bean;
@Entity
@Table(name="question_tag")
public class QuestionTag {

    @Id
    @Column(name="q_id")
    private Long questionId;

    @Column(name="t_id")
    private Long tagId;

    public QuestionTag() {
    }

    public QuestionTag(Long questionId, Long tagId) {
        this.questionId = questionId;
        this.tagId = tagId;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }
}
