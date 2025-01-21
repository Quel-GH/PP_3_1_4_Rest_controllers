package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserServiceImp;

import java.security.Principal;


@Controller
public class UserController {
    private UserServiceImp userService;

    @Autowired
    public void setUserService(UserServiceImp userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/user")
    public String userInfo(Principal principal, Model model) {
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("user", user);
        return "bootstrap_user";
    }

}
