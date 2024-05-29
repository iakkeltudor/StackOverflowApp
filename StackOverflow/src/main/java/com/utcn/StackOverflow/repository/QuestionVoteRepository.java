package com.utcn.StackOverflow.repository;

import com.utcn.StackOverflow.entity.Answer;
import com.utcn.StackOverflow.entity.Question;
import com.utcn.StackOverflow.entity.QuestionVote;
import com.utcn.StackOverflow.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionVoteRepository extends JpaRepository<QuestionVote, Long> {
    Optional<QuestionVote> findByUserAndQuestion(User user, Question question);

    List<QuestionVote> findAllByQuestion(Question question);

    boolean existsByUserAndQuestion(User user, Question question);

    boolean existsByUserAndAnswer(User user, Answer answer);
}
