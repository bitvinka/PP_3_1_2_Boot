package com.example.boot.controller;


import com.example.boot.model.User;
import com.example.boot.service.UserService;
import com.example.boot.util.UserValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;


@Controller
public class UserController {
    private final UserService userService;
    private final UserValidator userValidator;
    private static final String REDIRECT = "redirect:/";

    @Autowired
    public UserController(UserService userService, UserValidator userValidator) {
        this.userService = userService;
        this.userValidator = userValidator;
    }


    @GetMapping("/")
    public String allUsers(Model model) {
        model.addAttribute("users", userService.getUsers());
        return "index";
    }

    @GetMapping("/user")
    public String getUserById(@RequestParam("id") Long id, Model model){
        Optional <User> optUser = userService.getUser(id);
        optUser.ifPresent(user -> model.addAttribute("user", user));
        return "user";
    }


    @GetMapping("/users/new")
    public String addUser(Model model)
    {
        model.addAttribute("user", new User());
        return "newUserForm";
    }

    @PostMapping("/addNewUser")
    public String addUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        userValidator.validate(user, bindingResult);
        if(bindingResult.hasErrors()){
            return "newUserForm";
        }
        userService.addUser(user);
        return REDIRECT;
    }


    @GetMapping("/user/edit")
    public String editUser(@RequestParam(value = "id") Long id, Model model) {
        Optional <User> optUser = userService.getUser(id);
        optUser.ifPresent(user -> model.addAttribute("editUser", user));
        return "editUserForm";
    }

    @PostMapping("/user/edit")
    public String editUserForm(@ModelAttribute("editUser") @Valid User user, BindingResult bindingResult, @RequestParam(value = "id") Long id) {
        Optional <User> optUser = userService.getUser(user.getId());
        if(optUser.isPresent() && (!user.getEmail().equals(optUser.get().getEmail()))) {
                userValidator.validate(user, bindingResult);
        }
        if(bindingResult.hasErrors()){
            return "editUserForm";
        }
        userService.updateUser(id, user);
        return REDIRECT;
    }

    @GetMapping("/user/delete")
    public String deleteUser(@RequestParam(value = "id") Long id) {
        userService.removeUser(id);
        return REDIRECT;
    }


}
