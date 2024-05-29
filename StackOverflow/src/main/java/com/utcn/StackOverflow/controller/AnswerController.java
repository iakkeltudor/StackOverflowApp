package com.utcn.StackOverflow.controller;

import com.utcn.StackOverflow.entity.Answer;
import com.utcn.StackOverflow.entity.Question;
import com.utcn.StackOverflow.entity.User;
import com.utcn.StackOverflow.request.DeleteAnswerRequest;
import com.utcn.StackOverflow.request.InsertAnswerRequest;
import com.utcn.StackOverflow.request.VoteRequest;
import com.utcn.StackOverflow.service.AnswerService;
import com.utcn.StackOverflow.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@CrossOrigin("*")
@RestController
@RequestMapping("/answers")
public class AnswerController {
    @Autowired
    private AnswerService answerService;
    @Autowired
    private UserService userService;

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
        String imagePath = insertAnswerRequest.getImagePath();

        User author = userService.retrieveById(authorId);
        if (author == null) {
            throw new RuntimeException("Author not found");
        }

        Answer answer = new Answer(author, text, creationDateTime, questionId, imagePath);

        return this.answerService.insertAnswer(answer);
    }

    @PutMapping("updateAnswer/{id}")
    @ResponseBody
    public Answer updateAnswer(@PathVariable Long id, @RequestBody InsertAnswerRequest insertAnswerRequest) {
        Long authorId = insertAnswerRequest.getAuthorId();
        //User author = insertAnswerRequest.getAuthor();
        String text = insertAnswerRequest.getText();
        LocalDateTime creationDateTime = insertAnswerRequest.getCreationDateTime();
        Long questionId = insertAnswerRequest.getQuestionId();
        String imagePath = insertAnswerRequest.getImagePath();

        User author = userService.retrieveById(authorId);
        if (author == null) {
            throw new RuntimeException("Author not found");
        }

        //Answer answer = new Answer(authorId, text, creationDateTime, questionId);

        return this.answerService.updateAnswer(id, author, text, creationDateTime, questionId, imagePath);
    }

    @DeleteMapping("/deleteAnswer/{id}")
    @ResponseBody
    public String deleteAnswer(@PathVariable Long id) {
        return this.answerService.deleteById(id);
    }

    @PostMapping("/upVote/{id}")
    @ResponseBody
    public String upVote(@PathVariable Long id, @RequestBody VoteRequest voteRequest) {
        Long userId = voteRequest.getUserId();

        Answer answer = answerService.retrieveById(id);

        User userOfAnswer = answer.getAuthor();

        if (Objects.equals(userOfAnswer.getID(), userId)) {
            return "You cannot vote on your own question.";
        } else {
            if (!answerService.hasUserVoted(userId, id)) {
                answer.setScore(answer.getScore() + 1);
                answerService.saveUserVote(userId, id, "upvote");
                answerService.updateScore(id, answer.getScore());
                return "Voted up successfully.";
            } else {
                return "You have already voted on this question.";
            }
        }
    }

    @PostMapping("/downVote/{id}")
    @ResponseBody
    public String downVote(@PathVariable Long id, @RequestBody VoteRequest voteRequest) {
        Long userId = voteRequest.getUserId();

        Answer answer = answerService.retrieveById(id);

        User userOfAnswer = answer.getAuthor();

        if (Objects.equals(userOfAnswer.getID(), userId)) {
            return "You cannot vote on your own question.";
        } else {
            if (!answerService.hasUserVoted(userId, id)) {
                answer.setScore(answer.getScore() - 1);
                answerService.saveUserVote(userId, id, "downvote");
                answerService.updateScore(id, answer.getScore());
                return "Voted up successfully.";
            } else {
                return "You have already voted on this question.";
            }
        }
    }
}
