package com.teamA.service.implementation;

import com.teamA.model.User;
import com.teamA.repository.UserRepository;
import com.teamA.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImplementation implements UserService {

    private UserRepository userRepository;

    @Autowired
    public UserServiceImplementation(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User createUser(User user) {
        userRepository
                .save(new User(user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword()));
        return user;
    }

    @Override
    public List<User> getAllUsers() {
        Iterable<User> users;
        users = userRepository.findAll();
        List<User> userList = new ArrayList<>();
        users.forEach(userList::add);
        return userList;
    }

    @Override
    public User updateUser(Long id, User updatedUser) {
        Optional<User> optionalUser = (userRepository.getUserById(id));
        if(optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setFirstName(updatedUser.getFirstName());
            user.setLastName(updatedUser.getLastName());
            user.setEmail(updatedUser.getEmail());
            user.setPassword(updatedUser.getPassword());
            return userRepository.save(user);
        } else {
            return null;
        }
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.getUserById(id);
    }

    @Override
    public void deleteUserById(User account) {
        userRepository.delete(account);
    }


}
