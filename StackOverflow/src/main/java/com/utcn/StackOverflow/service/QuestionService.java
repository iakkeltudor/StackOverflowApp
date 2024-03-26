package com.utcn.StackOverflow.service;

import com.utcn.StackOverflow.entity.Question;
import com.utcn.StackOverflow.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    public Question insertQuestion(Question question) {
        return questionRepository.save(question);
    }

    public List<Question> retrieveQuestions() {
        return (List<Question>) this.questionRepository.findAll();
    }

    public String deleteById(Long questionId) {
        try {
            questionRepository.deleteById(questionId);
            return "Question with ID " + questionId + " deleted successfully.";
        } catch (Exception e) {
            return "Failed to delete question with ID " + questionId;
        }
    }

    public Question updateQuestion(Long questionId, Long authorId, String newTitle, String newText, String creationDate, String creationTime) {
        try {
            Optional<Question> optionalQuestion = questionRepository.findById(questionId);
            if (optionalQuestion.isPresent()) {
                Question question = optionalQuestion.get();
                question.setID(questionId);
                question.setAuthorId(authorId);
                question.setDate(creationDate);
                question.setTime(creationTime);
                question.setTitle(newTitle);
                question.setText(newText);
                return questionRepository.save(question);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
