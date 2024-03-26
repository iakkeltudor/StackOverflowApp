package com.utcn.StackOverflow.repository;

import com.utcn.StackOverflow.entity.UserQuestion;
import org.springframework.data.repository.CrudRepository;

public interface UserQuestionRepository extends CrudRepository<UserQuestion, Long> {
}
