package com.example.demo.service;


import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service(value = "InMemory")
public class InMemoryUserRepositoryImpl implements UserRepository {

    private int userId = 1;

    private List<User> userList = new ArrayList<>();

    public void addUser(User user) {
        user.setUserId(userId);
        userList.add(user);
        userId++;
    }

    public List<User> getUsers() {
        return userList;
    }

    public User getUserById(int userId) {
        for (User user : userList)
            if (user.getUserId() == userId)
                return user;
        return null;
    }

    public boolean updateUserById(int userId, User user) {
        for (User userVariable : userList) {
            if (userVariable.getUserId() == userId) {
                userVariable.setName(user.getName());
                userVariable.setEmail(user.getEmail());
                return true;
            }
        }
        return false;
    }

    public boolean deleteUserById(int userId) {
        for (User userVariable : userList) {
            if (userVariable.getUserId() == userId) {
                userList.remove(userVariable);
                return true;
            }
        }
        return false;
    }

}
