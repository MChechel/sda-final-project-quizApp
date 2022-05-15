package com.teamA.service;

import com.teamA.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    void createUser(User user);

    List<User> getAllUsers();

    void updateUser(User user);

    Optional<User> getUserById(Long id);

    Optional<User> getUserByEmail(String email);

    void deleteUserById(Long id);

}
