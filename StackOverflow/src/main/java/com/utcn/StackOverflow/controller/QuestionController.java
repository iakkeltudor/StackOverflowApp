package com.utcn.StackOverflow.controller;

import com.utcn.StackOverflow.entity.Question;
import com.utcn.StackOverflow.entity.Tag;
import com.utcn.StackOverflow.entity.User;
import com.utcn.StackOverflow.repository.QuestionTagRepository;
import com.utcn.StackOverflow.request.InsertQuestionRequest;
import com.utcn.StackOverflow.request.TagFilterRequest;
import com.utcn.StackOverflow.request.VoteRequest;
import com.utcn.StackOverflow.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@CrossOrigin("*")
@RestController
@RequestMapping("/questions")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private UserService userService;

    @Autowired
    private TagService tagService;

    @Autowired
    private AnswerService answerService;
    @Autowired
    private QuestionVoteService questionVoteService;

    @GetMapping("/getAll")
    @ResponseBody
    public List<Question> retrieveAllQuestions() {
        return this.questionService.retrieveQuestions();
    }

    @GetMapping("/getOwnQuestions/{authorId}")
    @ResponseBody
    public List<Question> retrieveByAuthor(@PathVariable Long authorId) {
        User author = userService.retrieveById(authorId);
        if (author == null) {
            throw new RuntimeException("Author not found");
        }
        return this.questionService.retrieveByAuthor(author);
    }

    @GetMapping("/getByUsername/{username}")
    @ResponseBody
    public List<Question> retrieveByUsername(@PathVariable String username) {
        User author = userService.retrieveByUsername(username);
        if (author == null) {
            throw new RuntimeException("Author not found");
        }
        return this.questionService.retrieveByAuthor(author);
    }

    @GetMapping("/getByTitle/{title}")
    @ResponseBody
    public List<Question> retrieveByText(@PathVariable String title) {
        return this.questionService.retrieveByTitle(title);
    }

    @GetMapping("/getByTag/{tag}")
    @ResponseBody
    public List<Question> retrieveByTag(@PathVariable String tag) {
        Tag newTag = tagService.findByTagName(tag);
        return this.questionService.retrieveByTag(newTag);
    }

    @CrossOrigin("http://localhost:4200")
    @PostMapping("/insertQuestion")
    @ResponseBody
    public Question insertQuestion(@RequestBody InsertQuestionRequest insertQuestionRequest) {
        Long authorId = insertQuestionRequest.getAuthorId();
        String title = insertQuestionRequest.getTitle();
        String text = insertQuestionRequest.getText();
        LocalDateTime creationDateTime = insertQuestionRequest.getCreationDateTime();
        List<Tag> tags = insertQuestionRequest.getTags();
        String imagePath = insertQuestionRequest.getImagePath();

        User author = userService.retrieveById(authorId);
        if (author == null) {
            throw new RuntimeException("Author not found");
        }

        List<Tag> savedTags = new ArrayList<>();
        for (Tag tag : tags) {
            Tag savedTag = tagService.saveTag(tag);
            savedTags.add(savedTag);
        }

        Question question = new Question(author, title, text, creationDateTime, savedTags, imagePath);

        return this.questionService.insertQuestion(question);
    }

    @PutMapping("/updateQuestion/{id}")
    @ResponseBody
    public Question updateQuestion(@PathVariable Long id, @RequestBody InsertQuestionRequest insertQuestionRequest) {
        Long authorId = insertQuestionRequest.getAuthorId();
        String title = insertQuestionRequest.getTitle();
        String text = insertQuestionRequest.getText();
        LocalDateTime creationDateTime = insertQuestionRequest.getCreationDateTime();
        List<Tag> tags = insertQuestionRequest.getTags();
        String imagePath = insertQuestionRequest.getImagePath();

        System.out.println("authorId: " + authorId + " title: " + title + " text: " + text + " creationDateTime: " + creationDateTime + " tags: " + tags.toString() + " imagePath: " + imagePath);

        User author = userService.retrieveById(authorId);
        if (author == null) {
            throw new RuntimeException("Author not found");
        }

        List<Tag> savedTags = new ArrayList<>();
        for (Tag tag : tags) {
            Tag savedTag = tagService.saveTag(tag);
            savedTags.add(savedTag);
        }


        return this.questionService.updateQuestion(id, author, title, text, creationDateTime, savedTags, imagePath);
    }

    @DeleteMapping("/deleteQuestion/{id}")
    @ResponseBody
    public String deleteQuestion(@PathVariable Long id) {
        questionVoteService.deleteVotesByQuestionId(id);
        answerService.deleteAnswersByQuestionId(id);
        return this.questionService.deleteById(id);
    }

    @GetMapping("/getScore/{id}")
    @ResponseBody
    public int getScore(@PathVariable Long id) {
        return questionService.retrieveById(id).getScore();
    }

    @PostMapping("/upVote/{id}")
    @ResponseBody
    public String upVote(@PathVariable Long id, @RequestBody VoteRequest voteRequest) {
        Long userId = voteRequest.getUserId();

        Question question = questionService.retrieveById(id);

        User userOfQuestion = question.getAuthor(); //user who asked the question

        if (Objects.equals(userOfQuestion.getID(), userId)) {
            return "You cannot vote on your own question.";
        } else {
            if (!questionService.hasUserVoted(userId, id)) {
                question.setScore(question.getScore() + 1);
                questionService.saveUserVote(userId, id, "upvote");
                questionService.updateScore(id, question.getScore());
                return "Voted up successfully.";
            } else {
                return "You have already voted on this question.";
            }
        }
    }

    @PostMapping("/downVote/{id}")
    @ResponseBody
    public String downVote(@PathVariable Long id, @RequestBody VoteRequest voteRequest) {
        Question question = questionService.retrieveById(id);
        Long userId = voteRequest.getUserId();

        User userOfQuestion = question.getAuthor();

        if (Objects.equals(userOfQuestion.getID(), userId)) {
            return "You cannot vote on your own question.";
        } else {
            if (!questionService.hasUserVoted(userId, id)) {
                question.setScore(question.getScore() - 1);
                questionService.saveUserVote(userId, id, "downvote");
                questionService.updateScore(id, question.getScore());
                return "Voted down successfully.";
            } else {
                return "You have already voted on this question.";
            }
        }
    }
}
