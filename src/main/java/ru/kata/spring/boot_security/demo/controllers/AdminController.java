package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;
import javax.validation.Valid;
import java.security.Principal;


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

    @GetMapping()
    public String mainAdminPage(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        model.addAttribute("userList", userService.allUsers());
        String email = userDetails.getUsername();
        model.addAttribute("userEmail", email);
        model.addAttribute("role", userService.findByUsername(email).getRoles());
        return "bootstrap_admin";
    }

    @GetMapping("/new")
    public String newUserPage(Model model, Principal principal){
        model.addAttribute("userEmail", principal.getName());
        User user = userService.findByUsername(principal.getName());
        return "bootstrap_admin";
    }

    @PostMapping("/newUser")
    public String createNewUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "views/bootstrap_new";
        }
        try {
            userService.saveUser(user);
        } catch (RuntimeException e){
            bindingResult.rejectValue("email", "error.user", e.getMessage());
            return "views/bootstrap_new";
        }
        return  "redirect:/admin";
    }

    @GetMapping("/deleteUser")
    public String deleteUser(@RequestParam Long id, Model model) {
        model.addAttribute("user", userService.findUserById(id));
        return "views/bootstrap_delete";
    }
    @PostMapping("/delete")
    public String delete(@ModelAttribute("user") User user){
        userService.deleteUser(user.getId());
        return "redirect:/admin";
    }

    @PostMapping("/edit")
    public String updateUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()){
            return "views/bootstrap_edit";
        }
        try {
            userService.saveUser(user);
        } catch (RuntimeException e) {
            bindingResult.rejectValue("email", "error.user", e.getMessage());
            return "views/bootstrap_edit";
        }
        return "redirect:/admin";
    }

//  Добавление списка ролей для всех запросов
    @ModelAttribute
    public void addAttributes(Model model) {
        model.addAttribute("availableRoles", roleService.allRoles());
    }

}
