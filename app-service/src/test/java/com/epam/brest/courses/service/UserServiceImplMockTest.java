package com.epam.brest.courses.service;

import com.epam.brest.courses.dao.UserDao;
import com.epam.brest.courses.domain.User;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

import javax.swing.*;
import java.util.zip.DataFormatException;

/**
 * Created by alesya on 27.10.14.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:/spring-services-mock.xml" })
public class UserServiceImplMockTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserDao userDao;

    @After
    public void clean(){
        reset(userDao);
    }

    @Test
    public void addUser(){

        User user = UserDataFixture.getNewUser();

        userDao.getUserByLogin(user.getLogin());
        expectLastCall().andReturn(null);
        userDao.addUser(user);
        expectLastCall();

        replay(userDao);

        userService.addUser(user);

        verify(userDao);

    }

    @Test
    public void addUser2(){

        User user = UserDataFixture.getNewUser();

        userDao.getUserByLogin(user.getLogin());
        expectLastCall().andReturn(null).times(2);

        userDao.addUser(user);
        expectLastCall().times(2);

        replay(userDao);

        userService.addUser(user);
        userService.addUser(user);

        verify(userDao);

    }

    @Test
    public void testGetUserByLogin(){
        User user = UserDataFixture.getExistUser(1L);

//        userDao.getUserByLogin(user.getLogin());
//        expectLastCall().andReturn(user);

        expect(userDao.getUserByLogin(user.getLogin())).andReturn(user);

        replay(userDao);

        User userResult = userService.getUserByLogin(user.getLogin());

        verify(userDao);

        assertSame(user, userResult);

    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddUserWithSameLogin(){

        User user = UserDataFixture.getNewUser();

        expect(userDao.getUserByLogin(user.getLogin())).andReturn(user);

        replay(userDao);

        userService.addUser(user);

    }


   @Test(expected = NumberFormatException.class)
   //@Test(expected = DataFormatException.class)
    public void testAddUserException(){

        User user = UserDataFixture.getNewUser();

        expect(userDao.getUserByLogin(user.getLogin())).andThrow( new NumberFormatException());

        replay(userDao);

        userService.addUser(user);
    }
}



