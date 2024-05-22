package com.utcn.StackOverflow.service;

import com.utcn.StackOverflow.entity.Question;
import com.utcn.StackOverflow.entity.Tag;
import com.utcn.StackOverflow.entity.User;
import com.utcn.StackOverflow.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
        return questionRepository.findAllByOrderByCreationDateTimeDesc();
    }

    public String deleteById(Long questionId) {
        try {
            questionRepository.deleteById(questionId);
            return "Question with ID " + questionId + " deleted successfully.";
        } catch (Exception e) {
            return "Failed to delete question with ID " + questionId;
        }
    }

    public Question updateQuestion(Long questionId, User author, String newTitle, String newText, LocalDateTime creationDateTime, List<Tag> tags, String imagePath) {
        try {
            Optional<Question> optionalQuestion = questionRepository.findById(questionId);
            if (optionalQuestion.isPresent()) {
                Question question = optionalQuestion.get();
                question.setID(questionId);
                question.setAuthor(author);
                question.setCreationDateTime(creationDateTime);
                question.setTitle(newTitle);
                question.setText(newText);
                question.setTags(tags);
                question.setImagePath(imagePath);
                return questionRepository.save(question);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
