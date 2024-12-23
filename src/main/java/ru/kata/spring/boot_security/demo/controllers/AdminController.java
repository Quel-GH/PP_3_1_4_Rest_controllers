package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;


@RequestMapping("/admin")
@Controller
public class AdminController {
    private UserService userService;
    private RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/main")
    public String mainAdmin(Model model) {
        model.addAttribute("userList", userService.allUsers());
        return "/main";
    }

//    TODO Перенести


    @GetMapping(value = "/add")
    public String add(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("availableRoles", roleService.allRoles());
        return "add";
    }

    @PostMapping(value = "/add")
    public String add(@ModelAttribute("user") User user) {
        userService.saveUser(user);
        return "redirect:/admin/main";
    }

    @GetMapping(value = "/delete")
    public String delete() {
        return "delete";
    }

    @PostMapping(value = "/delete")
    public String delete(@RequestParam("Id") Long Id) {
        userService.deleteUser(Id);
        return "redirect:/admin/main";
    }

    @GetMapping(value = "/update")
    public String update(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("availableRoles", roleService.allRoles());
        return "update";
    }

    @PostMapping(value = "/update")
    public String update(@ModelAttribute("user") User user) {
        userService.saveUser(user);
        return "redirect:/admin/main";
    }
}
