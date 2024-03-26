package com.utcn.StackOverflow.controller;

import com.utcn.StackOverflow.entity.Question;
import com.utcn.StackOverflow.service.QuestionService;
import com.utcn.StackOverflow.service.UserQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/questions")
public class QuestionController {

    @Autowired
    private QuestionService questionService;
    @Autowired
    private UserQuestionService userQuestionService;

    @GetMapping("/getAll")
    @ResponseBody
    public List<Question> retrieveAllQuestions() {
        return this.questionService.retrieveQuestions();
    }

    @PostMapping("/insertQuestion")
    @ResponseBody
    public Question insertQuestion(@RequestBody InsertQuestionRequest insertQuestionRequest) {
        Long id = insertQuestionRequest.getId();
        Long authorId = insertQuestionRequest.getAuthorId();
        String title = insertQuestionRequest.getTitle();
        String text = insertQuestionRequest.getText();
        String creationDate = insertQuestionRequest.getCreationDate();
        String creationTime = insertQuestionRequest.getCreationTime();

        Question question = new Question(id, authorId, title, text, creationDate, creationTime);

        return this.questionService.insertQuestion(question);
        //userQuestionService.associateUserWithQuestion(authorId, id);

        //return insertedQuestion;
    }

    @PutMapping("/updateQuestion")
    @ResponseBody
    public Question updateQuestion(@RequestBody InsertQuestionRequest insertQuestionRequest) {
        Long id = insertQuestionRequest.getId();
        Long authorId = insertQuestionRequest.getAuthorId();
        String title = insertQuestionRequest.getTitle();
        String text = insertQuestionRequest.getText();
        String creationDate = insertQuestionRequest.getCreationDate();
        String creationTime = insertQuestionRequest.getCreationTime();

        return this.questionService.updateQuestion(id, authorId, title, text, creationDate, creationTime);
    }

    @DeleteMapping("/deleteQuestion")
    @ResponseBody
    public String deleteQuestion(@RequestBody DeleteQuestionRequest deleteQuestionRequest) {
        Long id = deleteQuestionRequest.getId();
        return this.questionService.deleteById(id);
    }
}
