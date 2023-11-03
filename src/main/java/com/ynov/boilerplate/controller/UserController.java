package com.ynov.boilerplate.controller;

import com.ynov.boilerplate.entity.User;
import com.ynov.boilerplate.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/auth/users")
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(ArticleController.class);
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public ResponseEntity<List<User>> articles() {
        log.info(" -----> : tous les articles ");
        List<User> users = userService.getAllUser();
        log.info(users.toString());
        return new ResponseEntity<List<User>>(users, HttpStatus.OK);
    }
}
