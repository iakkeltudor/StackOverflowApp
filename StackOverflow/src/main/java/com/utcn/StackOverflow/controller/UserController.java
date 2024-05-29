package com.utcn.StackOverflow.controller;

import com.utcn.StackOverflow.entity.User;
import com.utcn.StackOverflow.request.BanRequest;
import com.utcn.StackOverflow.request.DeleteUserRequest;
import com.utcn.StackOverflow.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/getAll")
    @ResponseBody
    public List<User> retrieveAllUsers() {
        return this.userService.retrieveUsers();
    }

    @GetMapping("/getById/{id}")
    @ResponseBody
    public User retrieveById(@PathVariable Long id) {
        return this.userService.retrieveById(id);
    }

//    @PutMapping("/updateUser")
//    @ResponseBody
//    public User updateUser(@RequestBody UpdateUserRequest updateUserRequest) {
//        Long userId = Long.parseLong(updateUserRequest.getId());
//        String newUsername = updateUserRequest.getNewUsername();
//        String newPassword = updateUserRequest.getNewPassword();
//        return this.userService.updateUser(userId, newUsername, newPassword);
//    }

    @DeleteMapping("/deleteUser")
    @ResponseBody
    public String deleteUser(@RequestBody DeleteUserRequest deleteUserRequest) {
        Long id = deleteUserRequest.getId();
        return this.userService.deleteById(id);
    }

    @GetMapping("/getPoints/{userId}")
    @ResponseBody
    public float getPoints(@PathVariable Long userId) {
        return this.userService.getPoints(userId);
    }

    @PostMapping("/ban/{userId}")
    @ResponseBody
    public String banUser(@PathVariable Long userId, @RequestBody BanRequest banRequest) {
        String reason = banRequest.getReason();
        System.out.println("Reason: " + reason);
        return this.userService.banUser(userId, reason);
    }

    @PostMapping("/unban/{userId}")
    @ResponseBody
    public String unbanUser(@PathVariable Long userId) {
        return this.userService.unbanUser(userId);
    }

    @GetMapping("/checkBan/{username}")
    @ResponseBody
    public int checkBan(@PathVariable String username) {
        return this.userService.checkBan(username);
    }

}
