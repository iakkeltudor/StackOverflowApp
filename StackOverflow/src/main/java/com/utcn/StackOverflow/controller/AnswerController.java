package com.utcn.StackOverflow.controller;

import com.utcn.StackOverflow.entity.Answer;
import com.utcn.StackOverflow.entity.Question;
import com.utcn.StackOverflow.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/answers")
public class AnswerController {
    @Autowired
    private AnswerService answerService;

    @GetMapping("/getAll")
    @ResponseBody
    public List<Answer> retrieveAllAnswers() {
        return this.answerService.retrieveAnswers();
    }

    @PostMapping("/insertAnswer")
    @ResponseBody
    public Answer insertAnswer(@RequestBody InsertAnswerRequest insertAnswerRequest) {
        Long id = insertAnswerRequest.getId();
        String author = insertAnswerRequest.getAuthor();
        String text = insertAnswerRequest.getText();
        String creationDate = insertAnswerRequest.getCreationDate();
        String creationTime = insertAnswerRequest.getCreationTime();
        Long questionId = insertAnswerRequest.getQuestionId();

        Answer answer = new Answer(id, author, text, creationDate, creationTime, questionId);

        return this.answerService.insertAnswer(answer);
    }

    @PutMapping("updateAnswer")
    @ResponseBody
    public Answer updateAnswer(@RequestBody InsertAnswerRequest insertAnswerRequest) {
        Long id = insertAnswerRequest.getId();
        String author = insertAnswerRequest.getAuthor();
        String text = insertAnswerRequest.getText();
        String creationDate = insertAnswerRequest.getCreationDate();
        String creationTime = insertAnswerRequest.getCreationTime();
        Long questionId = insertAnswerRequest.getQuestionId();

        //Answer answer = new Answer(id, author, text, creationDate, creationTime, questionId);

        return this.answerService.updateAnswer(id, author, text, creationDate, creationTime, questionId);
    }

    @DeleteMapping("/deleteAnswer")
    @ResponseBody
    public String deleteAnswer(@RequestBody DeleteAnswerRequest deleteAnswerRequest) {
        Long id = deleteAnswerRequest.getId();
        return this.answerService.deleteById(id);
    }
}
