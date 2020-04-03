package com.example.demo.service;

import com.example.demo.exceptions.UserNotFoundException;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class JPAUserRepoImpl implements UserRepository {

    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

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
    }

    @Override
    public List<User> getUsers() {
        openEntityManager();
        entityManager.getTransaction().begin();
        List<User> users = entityManager.createQuery("FROM User", User.class).getResultList();
        entityManager.getTransaction().commit();
        closeEntityManager();
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
            throw new UserNotFoundException("user not found");
        }
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
            throw new UserNotFoundException("user not found");
        }
        entityManager.getTransaction().begin();
        updatedUser.setName(user.getName());
        updatedUser.setEmail(user.getEmail());
        entityManager.getTransaction().commit();
        closeEntityManager();
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
            throw new UserNotFoundException("user not found");
        }
        entityManager.getTransaction().begin();
        entityManager.remove(user);
        entityManager.getTransaction().commit();
        closeEntityManager();
        return true;
    }
}
