package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.User;
import java.util.List;


public interface UserService {
    User findByUsername(String username);

    User findUserById(Long userId);

    List<User> allUsers();

    User saveUser(User user);

    boolean deleteUser(Long userId);

    User getCurrentUser();
}
