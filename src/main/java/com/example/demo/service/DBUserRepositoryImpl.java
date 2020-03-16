package com.example.demo.service;

import com.example.demo.mapper.UserMapper;
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
    public User getUserById(int userId) {
        String query = "select *from user where ID=?";
        User user = jdbcTemplate.query(query, new Object[]{userId}, new UserMapper()).get(0);
        return user;
    }

    @Override
    public boolean updateUserById(int userId, User user) {
        String query = "update user set name=? , email=? where ID=?";
        int isUpdated = jdbcTemplate.update(query, user.getName(), user.getEmail(), userId);
        return isUpdated != 0;
    }

    @Override
    public boolean deleteUserById(int userId) {
        String query = "delete from user where ID=?";
        int isDeleted = jdbcTemplate.update(query, userId);
        return isDeleted != 0;
    }
}
