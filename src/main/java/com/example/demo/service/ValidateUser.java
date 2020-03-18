package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class ValidateUser {

//    private UserRepository userRepository;
    private JdbcTemplate jdbcTemplate;

    public ValidateUser() {
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
//        userRepository = (DBUserRepositoryImpl) context.getBean("DataBaseImpl");
        jdbcTemplate=(JdbcTemplate)context.getBean("jdbcTemplate");
    }

    public boolean isValidUser(int userId) {
//        User user=userRepository.getUserById(userId);
        String query = "select *from user where ID=?";
        List<User> users = jdbcTemplate.query(query, new Object[]{userId}, new UserMapper());
        return users.size() != 0;
    }
}

