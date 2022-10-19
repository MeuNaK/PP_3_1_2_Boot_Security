package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping()
    public String allUsers(Model model) {

        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);

        return "main";
    }

    @GetMapping("/registration-user")
    public String addUser(Model model) {

        User user = new User();
        model.addAttribute("newUser", user);
        Set<Role> roleSet =  roleService.getSetRole();
        model.addAttribute("setRoles", roleSet );

        return "registration-user";
    }

    @GetMapping("/update-user/{id}")
    public String updateUser(@PathVariable("id") int id, Model model) {

        User user = userService.getUser(id);
        model.addAttribute("newUser", user);
        Set<Role> roleSet =  roleService.getSetRole();
        model.addAttribute("setRoles", roleSet );
        System.out.println(user.getRoles());

        return "registration-user";
    }

    @PostMapping()
    public String create(@ModelAttribute("newUser") User user) {

        userService.addUser(user);

        return "redirect:/admin";
    }

    @GetMapping("/delete-user/{id}")
    public String deleteUser(@PathVariable("id") int id) {

        userService.removeUser(id);

        return "redirect:/admin";
    }
}
