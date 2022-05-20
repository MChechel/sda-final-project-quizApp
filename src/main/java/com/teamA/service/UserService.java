package com.teamA.service;

import com.teamA.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User createUser(User user);

    List<User> getAllUsers();

    User updateUser(Long id, User updatedUser);

    Optional<User> getUserById(Long id);

    void deleteUserById(User account);

}
