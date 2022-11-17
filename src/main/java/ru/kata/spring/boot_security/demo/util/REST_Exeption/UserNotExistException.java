package ru.kata.spring.boot_security.demo.util.REST_Exeption;

public class UserNotExistException extends RuntimeException {
    public UserNotExistException(String message) {
        super(message);
    }
}
