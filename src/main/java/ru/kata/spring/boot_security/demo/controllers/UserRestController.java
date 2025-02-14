package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserServiceImp;


@RestController
@RequestMapping("/user")
public class UserRestController {
    private UserServiceImp userService;

    @Autowired
    public void setUserService(UserServiceImp userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<User> getUser() {
        User user = userService.getCurrentUser();
        return ResponseEntity.ok(user);
    }

}
