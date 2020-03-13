package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.InMemoryUserRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/users")
public class UserController {

//    @Autowired
//    @Qualifier("InMemory")
//    private UserRepository userRepository;

    private UserRepository userRepository;

    public UserController() {
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        userRepository = (InMemoryUserRepositoryImpl) context.getBean("InMemory");
    }

    @PostMapping
    public String addUser(@RequestBody User user) {
        userRepository.addUser(user);
        return "added user";
    }

    @GetMapping
    public List<User> getUsers() {
        return userRepository.getUsers();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable("id") int userId) {
        return userRepository.getUserById(userId);
    }

    @PutMapping("/{id}")
    public String updateUserById(@PathVariable("id") int userId, @RequestBody User user) {
        boolean isUpdated = userRepository.updateUserById(userId, user);
        if (isUpdated) {
            return "User Updated";
        } else {
            return "User Not Found";
        }
    }

    @DeleteMapping("/{id}")
    public String deleteUserById(@PathVariable("id") int userId) {
        boolean isDeleted = userRepository.deleteUserById(userId);
        if (isDeleted) {
            return "User Deleted";
        } else {
            return "User Not Found";
        }
    }
}
