package com.utcn.StackOverflow.service;

import com.utcn.StackOverflow.entity.Question;
import com.utcn.StackOverflow.entity.QuestionVote;
import com.utcn.StackOverflow.entity.Tag;
import com.utcn.StackOverflow.entity.User;
import com.utcn.StackOverflow.repository.QuestionRepository;
import com.utcn.StackOverflow.repository.QuestionVoteRepository;
import com.utcn.StackOverflow.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private QuestionVoteRepository questionVoteRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private TagService tagService;

    public Question insertQuestion(Question question) {
        return questionRepository.save(question);
    }

    public List<Question> retrieveQuestions() {
        List<Question> questions =  questionRepository.findAllByOrderByCreationDateTimeDesc();

        for(Question question : questions) {
            userService.getPoints(question.getAuthor().getID());
        }

        return questions;
    }

    public Question retrieveById(Long questionId) {
        Optional<Question> optionalQuestion = questionRepository.findById(questionId);
        return optionalQuestion.orElse(null);
    }

    public List<Question> retrieveByAuthor(User author) {
        List<Question> questions = questionRepository.findAllByAuthorOrderByCreationDateTimeDesc(author);

        for(Question question : questions) {
            userService.getPoints(question.getAuthor().getID());
        }

        return questions;
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

    public void updateScore(Long questionId, int score) {
        try {
            Optional<Question> optionalQuestion = questionRepository.findById(questionId);
            if (optionalQuestion.isPresent()) {
                Question question = optionalQuestion.get();
                question.setScore(score);
                questionRepository.save(question);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUserVote(Long userId, Long questionId, String vote) {
        Optional<User> userOpt = userRepository.findById(userId);
        Optional<Question> questionOpt = questionRepository.findById(questionId);

        if (userOpt.isPresent() && questionOpt.isPresent()) {
            User user = userOpt.get();
            Question question = questionOpt.get();

            questionVoteRepository.save(new QuestionVote(question, user, vote));

        } else {
            throw new IllegalArgumentException("User or Question not found");
        }
    }

    public boolean hasUserVoted(Long userId, Long questionId) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new IllegalArgumentException("Question not found"));

        User user = userService.retrieveById(userId);
        return questionVoteRepository.existsByUserAndQuestion(user, question);
    }

    public List<Question> retrieveByTag(Tag tag) {
        Optional<Tag> existingTagOpt = Optional.ofNullable(tagService.findByTagName(tag.getName()));

        List<Question> questions = new ArrayList<>();

        if (existingTagOpt.isPresent()) {
            Tag savedTag = existingTagOpt.get();
            questions = questionRepository.findAllByTags(Collections.singletonList(savedTag));
        } else {
            Tag savedTag = tagService.saveTag(tag);
            questions = questionRepository.findAllByTags(Collections.singletonList(savedTag));
        }

        questions.reversed();

        for(Question question : questions) {
            userService.getPoints(question.getAuthor().getID());
        }

        return questions;
    }

    public List<Question> retrieveByTitle(String title) {
        List<Question> questions = questionRepository.findAllByTitleContaining(title);
        //questions.reversed();

        for(Question question : questions) {
            userService.getPoints(question.getAuthor().getID());
        }

        return questions;
    }

}
