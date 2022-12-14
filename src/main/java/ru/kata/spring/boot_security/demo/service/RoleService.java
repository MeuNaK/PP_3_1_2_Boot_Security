package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.Role;

import java.util.Set;

public interface RoleService {

    void addRole(Role role);

    Set<Role> getSetRoles();

    Role findById(int id);

    Role getRole(int id);
}
