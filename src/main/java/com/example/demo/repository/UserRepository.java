
package com.example.demo.repository;

import com.example.demo.exceptions.UserNotFoundException;
import com.example.demo.model.User;

import java.util.List;

public interface UserRepository {
    void addUser(User user);

    List<User> getUsers();

    User getUserById(int userId) throws UserNotFoundException;

    boolean updateUserById(int userId, User user) throws UserNotFoundException;

    boolean deleteUserById(int userId) throws UserNotFoundException;
}