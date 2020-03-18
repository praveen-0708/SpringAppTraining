
package com.example.demo.repository;

import com.example.demo.model.User;

import java.util.List;

public interface UserRepository {
    void addUser(User user);

    List<User> getUsers();

    User getUserById(int userId);

    boolean updateUserById(int userId, User user);

    boolean deleteUserById(int userId);
}