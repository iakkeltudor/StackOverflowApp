package com.utcn.StackOverflow.repository;

import com.utcn.StackOverflow.entity.Question;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface QuestionRepository extends CrudRepository<Question, Long>, PagingAndSortingRepository<Question, Long> {

    List<Question> findAllByOrderByCreationDateTimeDesc();
}
