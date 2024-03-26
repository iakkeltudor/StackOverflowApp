package com.utcn.StackOverflow.service;

import com.utcn.StackOverflow.entity.Question;
import com.utcn.StackOverflow.entity.User;
import com.utcn.StackOverflow.entity.UserQuestion;
import com.utcn.StackOverflow.repository.QuestionRepository;
import com.utcn.StackOverflow.repository.UserQuestionRepository;
import com.utcn.StackOverflow.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserQuestionService {

    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;
    private final UserQuestionRepository userQuestionRepository;

    @Autowired
    public UserQuestionService(UserRepository userRepository, QuestionRepository questionRepository, UserQuestionRepository userQuestionRepository) {
        this.userRepository = userRepository;
        this.questionRepository = questionRepository;
        this.userQuestionRepository = userQuestionRepository;
    }

    @Transactional
    public void associateUserWithQuestion(Long userId, Long questionId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        Optional<Question> optionalQuestion = questionRepository.findById(questionId);

        Question question = optionalQuestion.get();
        User user = optionalUser.get();

        UserQuestion userQuestion = new UserQuestion();
        userQuestion.setUser(user);
        userQuestion.setQuestion(question);

        userQuestionRepository.save(userQuestion);
    }
}

