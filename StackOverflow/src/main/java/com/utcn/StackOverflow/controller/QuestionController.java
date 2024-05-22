package com.utcn.StackOverflow.controller;

import com.utcn.StackOverflow.entity.Question;
import com.utcn.StackOverflow.entity.Tag;
import com.utcn.StackOverflow.entity.User;
import com.utcn.StackOverflow.request.InsertQuestionRequest;
import com.utcn.StackOverflow.service.QuestionService;
import com.utcn.StackOverflow.service.TagService;
import com.utcn.StackOverflow.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

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

    @GetMapping("/getAll")
    @ResponseBody
    public List<Question> retrieveAllQuestions() {
        return this.questionService.retrieveQuestions();
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

        for (Tag tag : tags) {
            Tag savedTag = tagService.saveTag(tag);
            tags.set(tags.indexOf(tag), savedTag);
        }

        Question question = new Question(author, title, text, creationDateTime, tags, imagePath);

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

        User author = userService.retrieveById(authorId);
        if (author == null) {
            throw new RuntimeException("Author not found");
        }

        for (Tag tag : tags) {
            if (tag.getTagId() == null) {
                tagService.saveTag(tag);
            }
        }

        return this.questionService.updateQuestion(id, author, title, text, creationDateTime, tags, imagePath);
    }

    @DeleteMapping("/deleteQuestion/{id}")
    @ResponseBody
    public String deleteQuestion(@PathVariable Long id) {
        return this.questionService.deleteById(id);
    }
}
