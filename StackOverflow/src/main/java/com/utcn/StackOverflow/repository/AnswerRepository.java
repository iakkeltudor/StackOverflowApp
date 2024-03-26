package com.utcn.StackOverflow.repository;

import com.utcn.StackOverflow.entity.Answer;
import org.springframework.data.repository.CrudRepository;

public interface AnswerRepository extends CrudRepository<Answer, Long> {
}
