package com.utcn.StackOverflow.repository;

import com.utcn.StackOverflow.entity.Question;
import org.springframework.data.repository.CrudRepository;

public interface QuestionRepository extends CrudRepository<Question, Long> {
}
