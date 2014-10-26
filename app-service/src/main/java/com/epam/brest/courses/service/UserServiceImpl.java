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

    public static final String ADMIN = "admin";

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
            LOGGER.debug("getUserByLogin({}), Exception:{}",login, e.toString());
        }
        return user;
    }

    @Override
    public User getUserById(long userId){
        User user = null;
        try {
            user = userDao.getUserById(userId);
        } catch (EmptyResultDataAccessException e) {
            LOGGER.debug("getUserById({}), Exception:{}",userId, e.toString());
        }
        return user;
    }

    @Override
    public void removeUser(Long userId){
        userDao.removeUser(userId);

    }

    @Override
    public void updateUser(User user){

        Assert.notNull(user);
        Assert.notNull(user.getUserId());
        Assert.notNull(user.getLogin(), "User login should be specified.");
        Assert.notNull(user.getName(),  "User name should be specified.");

        User modifyUser;

        try{
            modifyUser = userDao.getUserById(user.getUserId());
        }
        catch(EmptyResultDataAccessException e) {
            LOGGER.debug("updateUser({}): exception:{}", user, e.toString() );
            return;
        }

        // Verify modifyUser is upgradable
        if (modifyUser.getLogin() == ADMIN){
            LOGGER.debug("{}: User can not be modified", modifyUser);
            return;
        }
        //  Assert.isTrue(modifyUser.getLogin() == ADMIN," User can not be modified" );

        // Verify user parameters
        if ( user.getLogin() == ADMIN ){
            LOGGER.debug("It is not allowed new login <admin> " );
            return;
        }
        // Assert.isTrue(user.getLogin() == ADMIN, "It is not allowed new login <admin> " );

        try {
            userDao.updateUser(user);
        }
        catch (EmptyResultDataAccessException e) {
            LOGGER.debug("updateUser({}), Exception:{}",user, e.toString());
        }
    }

    @Override
    public List<User> getUsers(){
        return userDao.getUsers();
    }
}