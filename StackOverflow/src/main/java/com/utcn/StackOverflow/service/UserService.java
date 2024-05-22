package com.utcn.StackOverflow.service;

import com.utcn.StackOverflow.entity.User;
import com.utcn.StackOverflow.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

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
}
