package com.epam.brest.courses.service;

import com.epam.brest.courses.dao.UserDao;
import com.epam.brest.courses.domain.User;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.util.Assert;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * Created by alesya on 25.10.14.
 */
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LogManager.getLogger();

    private UserDao userDao;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void addUser(User user) {
        Assert.notNull(user);
        Assert.isNull(user.getUserId());
        Assert.notNull(user.getLogin(), "User login should be specified.");
        Assert.notNull(user.getName(), "User name should be specified.");
        User existingUser = getUserByLogin(user.getLogin());
        if (existingUser != null) {
            throw new IllegalArgumentException("User is present in DB");
        }
        userDao.addUser(user);
    }

    @Override
    public User getUserByLogin(String login) {
        User user = null;
        try {
            user = userDao.getUserByLogin(login);
        } catch (EmptyResultDataAccessException e) {
            //TODO: add logger
        }
        return user;
    }

    @Override
    public List<User> getUsers(){
        return userDao.getUsers();
    }

    @Override
    public void removeUser(Long userId){
        //TODO:
    }

    @Override
    public User getUserById(long userId){
        //TODO:
        return null;
    }

    @Override
    public void updateUser(User user){
        //TODO:
    }
}