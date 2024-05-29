package com.utcn.StackOverflow.service;

import com.utcn.StackOverflow.entity.Answer;
import com.utcn.StackOverflow.entity.QuestionVote;
import com.utcn.StackOverflow.entity.User;
import com.utcn.StackOverflow.repository.AnswerRepository;
import com.utcn.StackOverflow.repository.QuestionVoteRepository;
import com.utcn.StackOverflow.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class AnswerService {

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private QuestionVoteRepository questionVoteRepository;
    @Autowired
    private UserService userService;

    public Answer insertAnswer(Answer answer) {
        return answerRepository.save(answer);
    }

    public List<Answer> retrieveAnswers(Long id) {
        return this.answerRepository.findByQuestionIdOrderByScoreDesc(id);
    }

    public Answer retrieveById(Long answerId) {
        Optional<Answer> optionalAnswer = answerRepository.findById(answerId);
        return optionalAnswer.orElse(null);
    }

    public String deleteById(Long answerId) {
        try {
            answerRepository.deleteById(answerId);
            return "Answer with ID " + answerId + " deleted successfully.";
        } catch (Exception e) {
            return "Failed to delete answer with ID " + answerId;
        }
    }

    public Answer updateAnswer(Long answerId, User author, String newText, LocalDateTime creationDateTime, Long questionId, String imagePath) {
        try {
            Optional<Answer> optionalAnswer = answerRepository.findById(answerId);
            if (optionalAnswer.isPresent()) {
                Answer answer = optionalAnswer.get();
                answer.setID(answerId);
                answer.setAuthor(author);
                answer.setText(newText);
                answer.setCreationDateTime(creationDateTime);
                answer.setQuestionId(questionId);
                answer.setImagePath(imagePath);
                answerRepository.save(answer);
                return answer;
            }
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Transactional
    public void deleteAnswersByQuestionId(Long questionId) {
        answerRepository.deleteByQuestionId(questionId);
    }

    public void updateScore(Long answerId, int score) {
        try {
            Optional<Answer> optionalAnswer = answerRepository.findById(answerId);
            if (optionalAnswer.isPresent()) {
                Answer answer = optionalAnswer.get();
                answer.setScore(score);
                answerRepository.save(answer);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUserVote(Long userId, Long answerId, String vote) {
        Optional<User> userOpt = userRepository.findById(userId);
        Optional<Answer> answerOpt = answerRepository.findById(answerId);

        if (userOpt.isPresent() && answerOpt.isPresent()) {
            User user = userOpt.get();
            Answer answer = answerOpt.get();

            questionVoteRepository.save(new QuestionVote(answer, user, vote));
        } else {
            throw new IllegalArgumentException("User or Answer not found");
        }
    }

    public boolean hasUserVoted(Long userId, Long answerId) {
        Answer answer = answerRepository.findById(answerId)
                .orElseThrow(() -> new IllegalArgumentException("Answer not found"));

        User user = userService.retrieveById(userId);
        return questionVoteRepository.existsByUserAndAnswer(user, answer);
    }

}
