package com.example.demo.service;

import com.example.demo.exceptions.UserNotFoundException;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;
import java.util.logging.Level;

public class HibernateUserRepositoryImpl implements UserRepository {

    private SessionFactory factory;
    private Logger logger = Logger.getLogger(HibernateUserRepositoryImpl.class);

    public HibernateUserRepositoryImpl() {
        factory = new Configuration().configure().addAnnotatedClass(User.class).buildSessionFactory();
        java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.OFF);
    }

    @Override
    public void addUser(User user) {
        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(user);
        transaction.commit();
        session.close();
        logger.info("User added - " + user);
    }

    @Override
    public List<User> getUsers() {
        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();
        List<User> userList = session.createQuery("FROM User", User.class).list();
        transaction.commit();
        session.close();
        logger.info("List of users - " + userList);
        return userList;
    }

    @Override
    public User getUserById(int userId) throws UserNotFoundException {
        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();
        User user = session.get(User.class, userId);
        transaction.commit();
        session.close();
        if (user == null) {
            logger.info("User ID:" + userId + " not found");
            throw new UserNotFoundException("user not found");
        }
        logger.info("User:" + user);
        return user;
    }

    @Override
    public boolean updateUserById(int userId, User user) throws UserNotFoundException {
        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();
        User updatedUser = session.get(User.class, userId);
        if (updatedUser == null) {
            logger.info("User ID:" + userId + " not found");
            throw new UserNotFoundException("User not found");
        }
        updatedUser.setName(user.getName());
        updatedUser.setEmail(user.getEmail());
        try {
            session.update(updatedUser);
            transaction.commit();
            logger.info("User Updated");
            return true;
        } catch (HibernateException e) {
            logger.error("Error in updating user:" + e);
            transaction.rollback();
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public boolean deleteUserById(int userId) throws UserNotFoundException {
        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();
        User user = session.get(User.class, userId);
        if (user == null) {
            logger.info("User ID:" + userId + " not found");
            throw new UserNotFoundException("User not found");
        }
        try {
            session.delete(user);
            transaction.commit();
            logger.info("User Deleted");
            return true;
        } catch (HibernateException e) {
            logger.error("Error in deleting user:" + e);
            transaction.rollback();
            return false;
        } finally {
            session.close();
        }
    }
}
