package com.utcn.StackOverflow.service;

import com.utcn.StackOverflow.entity.QuestionVote;
import com.utcn.StackOverflow.entity.User;
import com.utcn.StackOverflow.repository.QuestionRepository;
import com.utcn.StackOverflow.repository.QuestionVoteRepository;
import com.utcn.StackOverflow.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private QuestionVoteRepository questionVoteRepository;

    @Autowired
    private EmailService emailService;

    public List<User> retrieveUsers() {
        return (List<User>) userRepository.findAll();
    }

    public User updateUser(Long userId, String newUsername, String newPassword) {
        try {
            Optional<User> optionalUser = userRepository.findById(userId);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                user.setUsername(newUsername);
                user.setPassword(newPassword);
                userRepository.save(user);
                return user;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public User insertUser(User user) {
        return userRepository.save(user);
    }

    public String deleteById(Long id) {
        try {
            userRepository.deleteById(id);
            return "User with id " + id + " was deleted successfully.";
        } catch (Exception e) {
            return "Failed to delete user with id " + id;
        }
    }

    public User retrieveById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        return optionalUser.orElse(null);
    }

    public float getPoints(Long userId) {
        List<QuestionVote> questionVotes = questionVoteRepository.findAll();
        User user = userRepository.findById(userId).orElse(null);

        float points = 0;

        for(QuestionVote questionVote : questionVotes) {
            //check if question exists
            if(questionVote.getQuestion() != null) {
                if(questionVote.getQuestion().getAuthor().getID() == userId) {
                    if (questionVote.getVote().equals("upvote")) {
                        points += 2.5F;
                    } else if (questionVote.getVote().equals("downvote")) {
                        points -= 1.5F;
                    }
                }
            }
            if(questionVote.getAnswer() != null) {
                if (questionVote.getAnswer().getAuthor().getID() == userId) {
                    if (questionVote.getVote().equals("upvote")) {
                        points += 5;
                    } else if (questionVote.getVote().equals("downvote")) {
                        points -= 2.5F;
                    }
                }
            }
            if (questionVote.getUser().getID() == userId && questionVote.getVote().equals("downvote")) {
                points -= 1.5F;
            }
        }

        if (user != null) {
            user.setPoints(points);
            userRepository.save(user);
        }

        return points;
    }

    public User retrieveByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public String banUser(Long userId, String reason) {
        User user = userRepository.findById(userId).orElse(null);
        if(user != null) {
            String subject = "Account Banned";
            String text = "Dear " + user.getUsername() + ",\n\nYour account has been banned for the following reason:\n" + reason + "\n\nRegards,\nStackOverflow Team";
            System.out.println("Sending email to " + user.getEmail());
            emailService.sendSimpleMessage(user.getEmail(), subject, text);
            user.setBanned(1);
            userRepository.save(user);
        }

        return "User has been banned.";
    }

    public String unbanUser(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if(user != null) {
            user.setBanned(0);
            userRepository.save(user);
        }
        return "User has been unbanned.";
    }

    public int checkBan(String username) {
        User user = userRepository.findByUsername(username);
        if(user != null) {
            System.out.println("User banned: " + user.getBanned());
            return user.getBanned();
        }
        return -1;
    }
}
