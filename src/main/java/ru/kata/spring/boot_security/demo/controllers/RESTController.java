package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.util.REST_Exeption.UserNotExistException;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class RESTController {

    private final RoleService roleService;

    private final UserService userService;

    @Autowired
    public RESTController(RoleService roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    @GetMapping("/roles")
    public Set<Role> getAllRoles() {
        return roleService.getSetRoles();
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/users/{id}")
    public User getUser(@PathVariable("id") int id) {
        User user = userService.getUser(id);

        if (user == null) {
            throw new UserNotExistException("User with ID=" + id + " not found in DB");
        }

        return userService.getUser(id);
    }

    @PostMapping("/users")
    public User addNewUser(@RequestBody User user) {
        userService.addUser(user);

        return user;
    }

    @PatchMapping("/users")
    public User updateUser(@RequestBody User user) {
        userService.addUser(user);

        return user;
    }

    @DeleteMapping("/users/{id}")
    public String deleteUser(@PathVariable("id") int id) {
        User user = userService.getUser(id);
        if (user == null) {
            throw new UserNotExistException("User with ID=" + id + " not found in DB");
        }

        userService.removeUser(id);

        return "User with ID=" + id + " has been deleted";
    }
}
