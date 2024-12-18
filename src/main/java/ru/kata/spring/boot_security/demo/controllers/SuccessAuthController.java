package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.model.Users;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;

@Controller
public class SuccessAuthController {
    private UserService userService;
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
    @Autowired
    public void setPasswordEncoder(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/admin/main")
    public String mainAdmin(Model model) {
        model.addAttribute("userList", userService.allUsers());
        return "main";
    }

    @GetMapping(value = "/user")
    public String userInfo(Principal principal, Model model) {
        Users user = userService.findByUsername(principal.getName());
        model.addAttribute("user", user);
        return "user";
    }

    @GetMapping(value = "/add")
    public String add(Model model){
        model.addAttribute("UserEntity", new Users());
        return "add";
    }

    @PostMapping(value = "/add")
    public String add(@RequestParam("username") String username, @RequestParam("password") String password) {
        userService.saveUser(new Users(username, passwordEncoder.encode(password)));
        return "redirect:/admin/main";
    }
    @GetMapping(value = "/delete")
    public String delete(){
        return "delete";
    }

    @PostMapping(value = "/delete")
    public String delete(@RequestParam("Id") Long Id) {
        userService.deleteUser(Id);
        return "redirect:/admin/main";
    }
    @GetMapping(value = "/update")
    public String update(){
        return "update";
    }

    @PostMapping(value = "/update")
    public String update(@RequestParam("id") Long id, @RequestParam("username") String username, @RequestParam("password") String password) {
        userService.saveUser(new Users(id, username, passwordEncoder.encode(password)));
        return "redirect:/admin/main";
    }
}
