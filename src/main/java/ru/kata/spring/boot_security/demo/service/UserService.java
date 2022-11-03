package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> getAllUsers();

    void addUser(User user);

    User getUser(int id);

    void removeUser(int id);

    void update(int id, User user);

    User getUserByUsername(String username);
}
