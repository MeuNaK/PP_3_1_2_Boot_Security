package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.HashSet;
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
    public String allUsers(Model model, Principal principal) {
        User authUser = userService.getUserByUsername(principal.getName());
        model.addAttribute("authUser", authUser);

        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);

        User newUser = new User();
        model.addAttribute("newUser", newUser);

        Set<Role> roleSet = new HashSet<>(roleService.getSetRoles());
        model.addAttribute("setRoles", roleSet);

        return "main";
    }

//    @GetMapping("/registration-user")
//    public String addUser(Model model) {
//
//        User user = new User();
//        model.addAttribute("newUser", user);
//        Set<Role> roleSet =  roleService.getSetRole();
//        model.addAttribute("setRoles", roleSet );
//
//        return "registration-user";
//    }

//    @GetMapping("/update-user/{id}")
//    public String editUser(@PathVariable("id") int id, Model model) {
//
//        User user = userService.getUser(id);
//        model.addAttribute("editUser", user);
//        Set<Role> roleSet =  roleService.getSetRole();
//        model.addAttribute("setRoles", roleSet );
//        System.out.println(user.getRoles());
//
//        return "update-user";
//    }

    @PatchMapping("/{id}")
    public String updateUser(@ModelAttribute("user") User user, @PathVariable("id") int id, int[] rolesID) {
        Set<Role> RoleList = new HashSet<>(rolesID.length);
        for (int i : rolesID) {
            RoleList.add(roleService.findById(i));
        }
        user.setRoles(RoleList);
        userService.update(id, user);

        return "redirect:/admin";
    }

    @PostMapping("/user-save")
    public String create(@ModelAttribute("newUser") User user, int[] rolesNewID) {
        Set<Role> RoleList = new HashSet<>(rolesNewID.length);
        for (int i : rolesNewID) {
            RoleList.add(roleService.findById(i));
        }
        user.setRoles(RoleList);
        userService.addUser(user);

        return "redirect:/admin";
    }

    @DeleteMapping("/delete-user/{id}")
    public String deleteUser(@PathVariable("id") int id) {

        userService.removeUser(id);

        return "redirect:/admin";
    }
}
