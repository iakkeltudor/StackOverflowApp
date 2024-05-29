package com.utcn.StackOverflow.request;

public class SaveVoteRequest {
    private Long userId;
    private String vote;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getVote() {
        return vote;
    }

    public void setVote(String vote) {
        this.vote = vote;
    }
}
