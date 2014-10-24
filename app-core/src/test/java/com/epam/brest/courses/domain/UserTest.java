package com.epam.brest.courses.domain;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UserTest  {

    User user;

    @Before
    public void setUp() throws Exception {
       user = new User();
    }

    @Test
    public void testGetUserName() throws Exception {
        user.setName("User Name");
        assertEquals("User Name",user.getName());

    }
}