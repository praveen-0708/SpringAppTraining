package com.example.demo.mapper;

import com.example.demo.model.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet resultSet, int i) throws SQLException {
        User user = new User();
        user.setUserId(resultSet.getInt("ID"));
        user.setEmail(resultSet.getString("email"));
        user.setName(resultSet.getString("name"));
        return user;
    }
}
