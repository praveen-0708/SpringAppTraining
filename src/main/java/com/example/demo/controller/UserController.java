package com.example.demo.controller;

import com.example.demo.exceptions.UserNotFoundException;
import com.example.demo.filter.AuthenticationFilter;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.HibernateUserRepositoryImpl;
import com.example.demo.service.ValidateUser;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@RestController
@RequestMapping("/home")
public class UserController {

//    @Autowired
//    @Qualifier("InMemory")
//    private UserRepository userRepository;

    private UserRepository userRepository;

    private Logger logger = Logger.getLogger(AuthenticationFilter.class);

    public UserController() {
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        userRepository = (HibernateUserRepositoryImpl) context.getBean("HibernateImpl");
    }


    @PostMapping("/users")
    public String addUser(@RequestBody User user) {
        userRepository.addUser(user);
        return "added user";
    }

    @GetMapping("/users")
    public List<User> getUsers() {
        return userRepository.getUsers();
    }

    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable("id") int userId) throws UserNotFoundException {
        return userRepository.getUserById(userId);
    }

    @PutMapping("/users/{id}")
    public String updateUserById(@PathVariable("id") int userId, @RequestBody User user) throws UserNotFoundException {
        boolean isUpdated = userRepository.updateUserById(userId, user);
        if (isUpdated) {
            return "User Updated";
        } else {
            return "User Not Found";
        }
    }

    @DeleteMapping("/users/{id}")
    public String deleteUserById(@PathVariable("id") int userId) throws UserNotFoundException {
        boolean isDeleted = userRepository.deleteUserById(userId);
        if (isDeleted) {
            return "User Deleted";
        } else {
            return "User Not Found";
        }
    }

    @GetMapping
    public ModelAndView homePage() {
        return new ModelAndView("index");
    }

    @PostMapping("/login")
    public String userLogIn(HttpServletRequest request, @RequestParam("userId") int userId) {
        ValidateUser validateUser = new ValidateUser();
        if (validateUser.isValidUser(userId)) {
            request.getSession(true).setAttribute("userId", userId);
            logger.info("logged in");
            return "logged in";
        } else {
            logger.info("invalid user ID");
            return "invalid user ID";
        }
    }

    @PostMapping("/logout")
    public String userLogOut(HttpServletRequest request) {
        request.getSession(false).invalidate();
        logger.info("logged out");
        return "logged out";
    }
}