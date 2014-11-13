package com.epam.brest.courses.service;

import com.epam.brest.courses.dao.UserDao;
import com.epam.brest.courses.domain.User;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.transaction.annotation.Transactional;
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
    @Transactional
    public Long addUser(User user) {
        Assert.notNull(user);
        Assert.isNull(user.getUserId());
        Assert.notNull(user.getLogin(),"User login should be specified.");
        Assert.notNull(user.getName(), "User name should be specified.");

        User existingUser = getUserByLogin(user.getLogin());

        if (existingUser != null) {
            throw new IllegalArgumentException(user + "is present in DB");
        }
        return userDao.addUser(user);
    }

    @Override
    @Transactional
    public User getUserByLogin(String login) {
        User user = null;
        try {
            user = userDao.getUserByLogin(login);
        } catch (EmptyResultDataAccessException e) {
            LOGGER.error("getUserByLogin({}), Exception:{}",login, e.toString());
        }
        return user;
    }

    @Override
    @Transactional
    public User getUserById(long userId){
        User user = null;
        try {
            user = userDao.getUserById(userId);
        } catch (EmptyResultDataAccessException e) {
            LOGGER.error("getUserById({}), Exception:{}",userId, e.toString());
        }
        return user;
    }

    @Override
    @Transactional
    public void removeUser(Long userId){
        userDao.removeUser(userId);
    }

    @Override
    @Transactional
    public void updateUser(User user){

        LOGGER.debug("updateUser({})", user );

        Assert.notNull(user);
        User modifyUser;

        try{
            modifyUser = userDao.getUserById(user.getUserId());
        }
        catch(EmptyResultDataAccessException e) {
            LOGGER.error("updateUser({}): exception:{}", user, e.toString() );
            return;
        }

        // Verify modifyUser is upgradable
        if (modifyUser.getLogin() == ADMIN) {
            if (user.getLogin() != ADMIN) {
                LOGGER.debug("updateUser: It is not allowed to change login admin");
                return;
            }
        }

        // Verify new user parameters
        if ( user.getLogin() == ADMIN ){
            if(modifyUser.getLogin() != ADMIN) {
                LOGGER.debug("It is not allowed to use login <admin> " );
                return;
            }
        }

        try {
            userDao.updateUser(user);
        }
        catch (EmptyResultDataAccessException e) {
            LOGGER.debug("updateUser({}), Exception:{}",user, e.toString());
        }
    }

    @Override
    @Transactional
    public List<User> getUsers(){
        return userDao.getUsers();
    }
}