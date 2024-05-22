package com.utcn.StackOverflow.repository;

import com.utcn.StackOverflow.entity.Answer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AnswerRepository extends CrudRepository<Answer, Long> {
    List<Answer> findByQuestionId(Long questionId);
}
