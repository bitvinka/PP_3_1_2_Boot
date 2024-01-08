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



@Controller
public class UserController {
    private final UserService userService;
    private final UserValidator userValidator;

    @Autowired
    public UserController(UserService userService, UserValidator userValidator) {
        this.userService = userService;
        this.userValidator = userValidator;
    }


    @GetMapping("/")
    public String getMain() {
        return "index";
    }

    @GetMapping("/showUserById")
    public String getUserById(@RequestParam(value = "id", required = false) Long id, Model model) {
        if (id == null) {
            return "redirect:/";
        }
        if (userService.getUser(id) == null) {
            model.addAttribute("message", "Пользователя с ID " + id + " не существует. ");
        }
        model.addAttribute("user", userService.getUser(id));
        return "user";

    }

    @GetMapping("/users")
    public String allUsers(Model model) {

        if (userService.getUsers().isEmpty()) {
            model.addAttribute("message", "Список пуст");
        }
        model.addAttribute("users", userService.getUsers());
        return "users";
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
        return "redirect:/users";
    }


    @GetMapping("/user/edit")
    public String editUser(@RequestParam(value = "id") Long id, Model model) {
        model.addAttribute("editUser", userService.getUser(id));
        return "editUserForm";
    }

    @PostMapping("/user/edit")
    public String editUserForm(@ModelAttribute("editUser") @Valid User user, BindingResult bindingResult, @RequestParam(value = "id") Long id) {
        userValidator.validate(user, bindingResult);
        if(bindingResult.hasErrors()){
            return "editUserForm";
        }
        userService.updateUser(id, user);
        return "redirect:/users";
    }

    @GetMapping("/user/delete")
    public String deleteUser(@RequestParam(value = "id") Long id) {
        userService.removeUser(id);
        return "redirect:/users";
    }


}
