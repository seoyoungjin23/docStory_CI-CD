package com.ky.docstory.controller;

import com.ky.docstory.common.dto.DocStoryResponseBody;
import com.ky.docstory.entity.User;
import com.ky.docstory.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public DocStoryResponseBody<User> createUser(@RequestParam String username) {
        User user = userService.createUser(username);
        return DocStoryResponseBody.success(user);
    }
}
