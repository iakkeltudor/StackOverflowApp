package com.utcn.StackOverflow.repository;

import com.utcn.StackOverflow.entity.QuestionTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionTagRepository extends JpaRepository<QuestionTag, Long>{
    void deleteByQuestionId(Long questionId);
}
