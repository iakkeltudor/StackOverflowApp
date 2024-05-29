package com.utcn.StackOverflow.service;

import com.utcn.StackOverflow.entity.Question;
import com.utcn.StackOverflow.entity.QuestionVote;
import com.utcn.StackOverflow.entity.User;
import com.utcn.StackOverflow.repository.QuestionRepository;
import com.utcn.StackOverflow.repository.QuestionVoteRepository;
import com.utcn.StackOverflow.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionVoteService {

    @Autowired
    private QuestionVoteRepository questionVoteRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuestionRepository questionRepository;

    public List<QuestionVote> getVotes(Long questionId) {
        System.out.println("questionId: " + questionId);
        Question question = questionRepository.findById(questionId).orElse(null);
        return questionVoteRepository.findAllByQuestion(question);
    }

    public void saveUserVote(Long userId, Long questionId, String vote) {
        Optional<User> userOpt = userRepository.findById(userId);
        Optional<Question> questionOpt = questionRepository.findById(questionId);

        if (userOpt.isPresent() && questionOpt.isPresent()) {
            User user = userOpt.get();
            Question question = questionOpt.get();

            Optional<QuestionVote> existingVote = questionVoteRepository.findByUserAndQuestion(user, question);

            if (existingVote.isPresent()) {
                QuestionVote questionVote = existingVote.get();
                questionVote.setVote(vote);
                questionVoteRepository.save(questionVote);
            } else {
                QuestionVote newVote = new QuestionVote();
                newVote.setUser(user);
                newVote.setQuestion(question);
                newVote.setVote(vote);
                questionVoteRepository.save(newVote);
            }
        } else {
            throw new IllegalArgumentException("User or Question not found");
        }
    }

    public void deleteVotesByQuestionId(Long questionId) {
        Question question = questionRepository.findById(questionId).orElse(null);
        List<QuestionVote> questionVotes = questionVoteRepository.findAllByQuestion(question);
        questionVoteRepository.deleteAll(questionVotes);
    }
}
