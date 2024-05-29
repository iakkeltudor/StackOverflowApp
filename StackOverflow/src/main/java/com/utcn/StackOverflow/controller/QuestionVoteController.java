package com.utcn.StackOverflow.controller;

import com.utcn.StackOverflow.entity.QuestionVote;
import com.utcn.StackOverflow.request.SaveVoteRequest;
import com.utcn.StackOverflow.service.QuestionVoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/question-vote")
public class QuestionVoteController {
    @Autowired
    private QuestionVoteService questionVoteService;

    @GetMapping("/getVotes/{questionId}")
    @ResponseBody
    public List<QuestionVote> getVotes(@PathVariable Long questionId) {
        return questionVoteService.getVotes(questionId);
    }

    @PostMapping("/saveUserVote/{questionId}")
    @ResponseBody
    public void saveUserVote(@PathVariable Long questionId, @RequestBody SaveVoteRequest saveVoteRequest) {
        Long userId = saveVoteRequest.getUserId();
        String vote = saveVoteRequest.getVote();
        System.out.println("userId: " + userId + " questionId: " + questionId + " vote: " + vote);
        //check if user has already voted

        questionVoteService.saveUserVote(userId, questionId, vote);
    }
}
