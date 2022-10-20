package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public void addUser(User user) {
        userRepository.save(user);
    }

    @Override
    @Transactional
    public User getUser(int id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElse(null);
    }

    @Override
    @Transactional
    public void removeUser(int id) {
        userRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void update(int id, User user) {
        User userToUpdate = userRepository.findById(id).orElseThrow( () -> new RuntimeException("User not found!"));

        userToUpdate.setUsername(user.getUsername());
        userToUpdate.setPassword(user.getPassword());
        userToUpdate.setAge(user.getAge());
        userToUpdate.setEmail(user.getEmail());
        userToUpdate.setRoles(user.getRoles());

        userRepository.save(userToUpdate);
    }

}
