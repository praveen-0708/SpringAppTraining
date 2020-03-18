package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ValidateUser {

    private UserRepository userRepository;

    public ValidateUser() {
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        userRepository = (DBUserRepositoryImpl) context.getBean("DataBaseImpl");
    }

    public boolean isValidUser(int userId) {
        User user=userRepository.getUserById(userId);
        return user != null;
    }
}
