package com.example.demo.service;

import com.example.demo.exceptions.UserNotFoundException;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.logging.Level;

public class JPAUserRepoImpl implements UserRepository {

    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private Logger logger = Logger.getLogger(JPAUserRepoImpl.class);

    public JPAUserRepoImpl() {
        java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.OFF);
    }

    private void openEntityManager() {
        entityManagerFactory = Persistence.createEntityManagerFactory("jpa");
        entityManager = entityManagerFactory.createEntityManager();
    }

    private void closeEntityManager() {
        entityManager.close();
        entityManagerFactory.close();
    }

    @Override
    public void addUser(User user) {
        openEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(user);
        entityManager.getTransaction().commit();
        closeEntityManager();
        logger.info("User added - " + user);
    }

    @Override
    public List<User> getUsers() {
        openEntityManager();
        entityManager.getTransaction().begin();
        List<User> users = entityManager.createQuery("FROM User", User.class).getResultList();
        entityManager.getTransaction().commit();
        closeEntityManager();
        logger.info("List of users - " + users);
        return users;
    }

    @Override
    public User getUserById(int userId) throws UserNotFoundException {
        openEntityManager();
        entityManager.getTransaction().begin();
        User user = entityManager.find(User.class, userId);
        entityManager.getTransaction().commit();
        closeEntityManager();
        if (user == null) {
            logger.info("User ID:" + userId + " not found");
            throw new UserNotFoundException("user not found");
        }
        logger.info("User:" + user);
        return user;
    }

    @Override
    public boolean updateUserById(int userId, User user) throws UserNotFoundException {
        openEntityManager();
        entityManager.getTransaction().begin();
        User updatedUser = entityManager.find(User.class, userId);
        entityManager.getTransaction().commit();
        if (updatedUser == null) {
            closeEntityManager();
            logger.info("User ID:" + userId + " not found");
            throw new UserNotFoundException("user not found");
        }
        entityManager.getTransaction().begin();
        updatedUser.setName(user.getName());
        updatedUser.setEmail(user.getEmail());
        entityManager.getTransaction().commit();
        closeEntityManager();
        logger.info("User Updated");
        return true;
    }

    @Override
    public boolean deleteUserById(int userId) throws UserNotFoundException {
        openEntityManager();
        entityManager.getTransaction().begin();
        User user = entityManager.find(User.class, userId);
        entityManager.getTransaction().commit();
        if (user == null) {
            closeEntityManager();
            logger.info("User ID:" + userId + " not found");
            throw new UserNotFoundException("user not found");
        }
        entityManager.getTransaction().begin();
        entityManager.remove(user);
        entityManager.getTransaction().commit();
        closeEntityManager();
        logger.info("User Deleted");
        return true;
    }
}
