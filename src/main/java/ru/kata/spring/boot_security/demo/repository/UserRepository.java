package ru.kata.spring.boot_security.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    // ищет юзера по 'username'
    // возвращает  Optional<User>, т. к. юзер может быть найден или не найден
    Optional<User> findByUsername(String username);

}
