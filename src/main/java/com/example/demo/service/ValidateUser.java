package com.example.demo.service;

import com.example.demo.model.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class ValidateUser {

    private JdbcTemplate jdbcTemplate;

    public ValidateUser() {
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        jdbcTemplate = (JdbcTemplate) context.getBean("jdbcTemplate");
    }

    public boolean isValidUser(int userId) {
        String query = "select *from user where ID=?";
        List<User> users = jdbcTemplate.query(query, new Object[]{userId}, new UserMapper());
        return users.size() != 0;
    }
}

