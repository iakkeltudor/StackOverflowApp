package com.utcn.StackOverflow.service;

import com.utcn.StackOverflow.entity.Answer;
import com.utcn.StackOverflow.repository.AnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AnswerService {

    @Autowired
    private AnswerRepository answerRepository;

    public Answer insertAnswer(Answer answer) {
        return answerRepository.save(answer);
    }

    public List<Answer> retrieveAnswers() {
        return (List<Answer>) this.answerRepository.findAll();
    }

    public String deleteById(Long answerId) {
        try {
            answerRepository.deleteById(answerId);
            return "Answer with ID " + answerId + " deleted successfully.";
        } catch (Exception e) {
            return "Failed to delete answer with ID " + answerId;
        }
    }

    public Answer updateAnswer(Long answerId, String author, String newText, String creationDate, String creationTime, Long questionId) {
        try {
            Optional<Answer> optionalAnswer = answerRepository.findById(answerId);
            if (optionalAnswer.isPresent()) {
                Answer answer = optionalAnswer.get();
                answer.setID(answerId);
                answer.setAuthor(author);
                answer.setText(newText);
                answer.setDate(creationDate);
                answer.setTime(creationTime);
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
