package com.utcn.StackOverflow.controller;

import com.utcn.StackOverflow.entity.Answer;
import com.utcn.StackOverflow.request.DeleteAnswerRequest;
import com.utcn.StackOverflow.request.InsertAnswerRequest;
import com.utcn.StackOverflow.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/answers")
public class AnswerController {
    @Autowired
    private AnswerService answerService;

    @GetMapping("/getAnswers/{question_id}")
    @ResponseBody
    public List<Answer> retrieveAllAnswers(@PathVariable Long question_id) {
        return this.answerService.retrieveAnswers(question_id);
    }

    @PostMapping("/insertAnswer")
    @ResponseBody
    public Answer insertAnswer(@RequestBody InsertAnswerRequest insertAnswerRequest) {
        Long authorId = insertAnswerRequest.getAuthorId();
        String text = insertAnswerRequest.getText();
        LocalDateTime creationDateTime = insertAnswerRequest.getCreationDateTime();
        Long questionId = insertAnswerRequest.getQuestionId();

        Answer answer = new Answer(authorId, text, creationDateTime, questionId);

        return this.answerService.insertAnswer(answer);
    }

    @PutMapping("updateAnswer/{id}")
    @ResponseBody
    public Answer updateAnswer(@PathVariable Long id, @RequestBody InsertAnswerRequest insertAnswerRequest) {
        Long authorId = insertAnswerRequest.getAuthorId();
        String text = insertAnswerRequest.getText();
        LocalDateTime creationDateTime = insertAnswerRequest.getCreationDateTime();
        Long questionId = insertAnswerRequest.getQuestionId();

        //Answer answer = new Answer(authorId, text, creationDateTime, questionId);

        return this.answerService.updateAnswer(id, authorId, text, creationDateTime, questionId);
    }

    @DeleteMapping("/deleteAnswer")
    @ResponseBody
    public String deleteAnswer(@RequestBody DeleteAnswerRequest deleteAnswerRequest) {
        Long id = deleteAnswerRequest.getId();
        return this.answerService.deleteById(id);
    }
}
