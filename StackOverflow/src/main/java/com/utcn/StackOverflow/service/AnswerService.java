package com.utcn.StackOverflow.service;

import com.utcn.StackOverflow.entity.Answer;
import com.utcn.StackOverflow.repository.AnswerRepository;
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

    public Answer insertAnswer(Answer answer) {
        return answerRepository.save(answer);
    }

    public List<Answer> retrieveAnswers(Long id) {
        return this.answerRepository.findByQuestionId(id);
    }

    public String deleteById(Long answerId) {
        try {
            answerRepository.deleteById(answerId);
            return "Answer with ID " + answerId + " deleted successfully.";
        } catch (Exception e) {
            return "Failed to delete answer with ID " + answerId;
        }
    }

    public Answer updateAnswer(Long answerId, Long authorId, String newText, LocalDateTime creationDateTime, Long questionId) {
        try {
            Optional<Answer> optionalAnswer = answerRepository.findById(answerId);
            if (optionalAnswer.isPresent()) {
                Answer answer = optionalAnswer.get();
                answer.setID(answerId);
                answer.setAuthorId(authorId);
                answer.setText(newText);
                answer.setCreationDateTime(creationDateTime);
                answer.setQuestionId(questionId);
                answerRepository.save(answer);
                return answer;
            }
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

}
