package com.epam.brest.courses.dao;
import com.epam.brest.courses.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import java.util.List;
import static org.junit.Assert.*;

/**
 * Created by alesya on 22.10.14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/testApplicationContextSpring.xml" })
public class UserDaoImplTest {

    @Autowired
    private UserDao userDao;

    @Test
    public void getUsers() {
        List<User> users = userDao.getUsers();
        assertNotNull(users);
        assertFalse(users.isEmpty());
    }

    @Test
    public void addUser(){
        List<User> users = userDao.getUsers();
        int sizeBefore = users.size();

        User user  = new User(3L,"UserLogin3","UserName3");

        userDao.addUser(user);
        users = userDao.getUsers();

        assertEquals(sizeBefore,users.size()-1);
    }

    @Test
    public void removeUser(){

        List<User> users = userDao.getUsers();

        int sizeBefore = users.size();
        userDao.removeUser(4L);
        users = userDao.getUsers();
        assertEquals(sizeBefore, users.size());

        userDao.removeUser(2L);
        users = userDao.getUsers();
        assertEquals(sizeBefore-1, users.size());
    }

    @Test
    public void getUserById(){

        Long userId = 1L;
        User user = userDao.getUserById(userId);
        assertNotNull(user);
        assertEquals(userId,user.getUserId());
    }

    @Test
    public void getUserByLogin(){

        String userLogin = "userLogin1";
        User user = userDao.getUserByLogin(userLogin);
        assertNotNull(user);
        assertEquals(userLogin,user.getLogin());
    }

    @Test
    public void  updateUser(){

        Long userId = 1L;
        String userLoginUpdate = "userLoginUpdate";
        String userNameUpdate = "userNameUpdate";

        userDao.updateUser(new User(userId,userLoginUpdate, userNameUpdate));

        User userUpdate = userDao.getUserById(userId);

        assertEquals( userLoginUpdate, userUpdate.getLogin() );
        assertEquals( userNameUpdate, userUpdate.getName() );

    }

}