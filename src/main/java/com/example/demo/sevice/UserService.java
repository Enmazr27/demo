package com.example.demo.sevice;

import com.example.demo.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getALl();
    Optional<User> getUserById(Long id);
    User saveUser(User user);
    User updateUser(Long id, User updatedUser);
    void deleteUser(Long id);
}
