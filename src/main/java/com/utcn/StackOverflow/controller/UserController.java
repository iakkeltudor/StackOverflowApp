package com.utcn.StackOverflow.controller;

import com.utcn.StackOverflow.entity.User;
import com.utcn.StackOverflow.request.DeleteUserRequest;
import com.utcn.StackOverflow.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/getAll")
    @ResponseBody
    public List<User> retrieveAllUser() {
        return this.userService.retrieveUsers();
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

}
