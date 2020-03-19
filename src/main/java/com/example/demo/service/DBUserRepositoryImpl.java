package com.example.demo.service;

import com.example.demo.exceptions.UserNotFoundException;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class DBUserRepositoryImpl implements UserRepository {

    private JdbcTemplate jdbcTemplate;

    public DBUserRepositoryImpl() {

    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addUser(User user) {
        String query = "insert into user(name,email) values(?,?)";
        jdbcTemplate.update(query, user.getName(), user.getEmail());
    }

    @Override
    public List<User> getUsers() {
        String query = "select *from user";
        List<User> userList = jdbcTemplate.query(query, new UserMapper());
        return userList;
    }

    @Override
    public User getUserById(int userId) throws UserNotFoundException {
        if (!new ValidateUser().isValidUser(userId)) {
            throw new UserNotFoundException("User Not Found");
        }
        String query = "select *from user where ID=?";
        List<User> users = jdbcTemplate.query(query, new Object[]{userId}, new UserMapper());
        if (users.size() != 0) {
            return users.get(0);
        } else {
            return null;
        }
    }

    @Override
    public boolean updateUserById(int userId, User user) throws UserNotFoundException {
        if (!new ValidateUser().isValidUser(userId)) {
            throw new UserNotFoundException("User Not Found");
        }
        String query = "update user set name=? , email=? where ID=?";
        int isUpdated = jdbcTemplate.update(query, user.getName(), user.getEmail(), userId);
        return isUpdated != 0;
    }

    @Override
    public boolean deleteUserById(int userId) throws UserNotFoundException {
        if (!new ValidateUser().isValidUser(userId)) {
            throw new UserNotFoundException("User Not Found");
        }
        String query = "delete from user where ID=?";
        int isDeleted = jdbcTemplate.update(query, userId);
        return isDeleted != 0;
    }
}