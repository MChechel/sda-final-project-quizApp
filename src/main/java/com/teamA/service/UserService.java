package com.teamA.service;

import com.teamA.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User createUser(User user);

    List<User> getAllUsers();

    User getUserByEmail(String email);

    User updateUser(Long id, User updatedUser);


    Optional<User> getUserById(Long id);

    Optional<User> getUserWithEmail(String email);


    void deleteUserById(User account);


}
