package com.epam.brest.courses.service;

import com.epam.brest.courses.domain.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:/testServiceApplicationContextSpring.xml" })
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=false)
@Transactional
public class UserServiceImplTest {
    public static final String ADMIN = "admin";

    @Autowired
    private UserService userService;

    @Before
    public void setUp() throws Exception {
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddNullUser() throws Exception {
        userService.addUser(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddEmptyUser() throws Exception {
        userService.addUser(new User());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddNotNullIdUser() throws Exception {
        userService.addUser(new User(12L, "", ""));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddUserWithSameLogin() throws Exception {
        userService.addUser(new User(null, ADMIN, ADMIN));
        userService.addUser(new User(null, ADMIN, ADMIN));
    }

    @Test
    public void testAddUser() throws Exception {
        userService.addUser(new User(null, ADMIN, ADMIN));
        User user = userService.getUserByLogin(ADMIN);
        Assert.assertEquals(ADMIN, user.getLogin());
    }

    @Test
    public void testGetUserByLogin(){
        //TODO:
    }

    @Test
    public void testGetUserById(){

        //TODO:
    }

    @Test
    public void testRemoveUser(){
        //TODO:
    }

    @Test
    public void testUpdateAdminUserLogin(){
        //TODO:

        try{
            userService.addUser(new User(null, ADMIN, ADMIN));
        }
        catch (IllegalArgumentException e){
        }

        User adminUser = userService.getUserByLogin(ADMIN);

        userService.updateUser(new User(adminUser.getUserId(), "newLogin", "newName"));

        User adminUserUpdated = userService.getUserById(adminUser.getUserId());

        assertEquals(adminUser.getLogin(), adminUserUpdated.getLogin());
        assertEquals(adminUser.getName(), adminUserUpdated.getName());
    }

    @Test
    public void testUpdateAdminUserName(){

        String NEW_NAME = "newName";

        try{
            userService.addUser(new User(null, ADMIN, ADMIN));
        }
        catch (IllegalArgumentException e){
        }

        User adminUser = userService.getUserByLogin(ADMIN);
        assertNotNull(adminUser);

        adminUser.setName(NEW_NAME);

        userService.updateUser(adminUser);

        User adminUserUpdated = userService.getUserById(adminUser.getUserId());
        assertNotNull(adminUserUpdated);

        assertEquals(adminUser.getLogin(), ADMIN);
        assertEquals(adminUser.getName(),  NEW_NAME);
    }

    @Test
    public void testUpdateUserWithAdminLogin(){
        Long userId = 1L;
        String userNameUpdate = "userNameUpdate";

        User userPrevious = userService.getUserById(userId);

        userService.updateUser(new User(userId,ADMIN, userNameUpdate));

        User userUpdated = userService.getUserById(userId);

        assertEquals( userPrevious.getLogin(), userUpdated.getLogin() );
        assertEquals( userPrevious.getName(), userUpdated.getName() );
    }

    @Test
    public void testUpdateUser(){

        Long userId = 1L;
        String userLoginUpdate = "userLoginUpdate";
        String userNameUpdate = "userNameUpdate";

        userService.updateUser(new User(userId,userLoginUpdate, userNameUpdate));

        User userUpdate = userService.getUserById(userId);

        assertEquals( userLoginUpdate, userUpdate.getLogin() );
        assertEquals( userNameUpdate, userUpdate.getName() );
    }

    @Test
    public void testGetUsers(){
        //TODO:
    }

}