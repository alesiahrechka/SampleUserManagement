package com.epam.brest.courses.client;

import com.epam.brest.courses.domain.User;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;

import static org.junit.Assert.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

/**
 * Created by alesya on 14.11.14.
 */
public class RestClientTest {

    static private final String HOST = "http://host";

    private RestClient client;

    private MockRestServiceServer mockServer;

    @Before
    public void setUp() {
        client = new RestClient(HOST);
        mockServer = MockRestServiceServer.createServer(client.getRestTemplate());
    }

    @After
    public void check() {
        mockServer.verify();
    }

    @Test
    public void versionTest() {
        mockServer.expect(requestTo(HOST + "/version"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess("123", MediaType.APPLICATION_JSON));

        String version = client.getRestVesrsion();
        assertEquals("123", version);
    }

    @Test
    public void addUserTest() {
        mockServer.expect(requestTo(HOST + "/users"))
                .andExpect(method(HttpMethod.POST))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(content().string("{\"userId\":null,\"login\":\"login1\",\"name\":\"name1\"}"))
                .andRespond(withSuccess("4", MediaType.APPLICATION_JSON));

        User user = new User();
        user.setLogin("login1");
        user.setName("name1");
        long id = client.addUser(user);
        assertEquals(4, id);
    }

    @Test
    public void getUserById() {
        mockServer.expect(requestTo(HOST + "/users/1"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess("{\"userId\":1,\"login\":\"login1\",\"name\":\"name1\"}", MediaType.APPLICATION_JSON));

        User user = client.getUserById(1);
        assertNotNull(user);
        assertNotNull(user.getUserId());
        assertEquals(new Long(1), user.getUserId());
        assertNotNull(user.getLogin());
        assertEquals("login1", user.getLogin());
        assertNotNull(user.getName());
        assertEquals("name1", user.getName());
    }

    @Test
    public void getUsers() {
        mockServer.expect(requestTo(HOST + "/users"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess("[{\"userId\":1,\"login\":\"login1\",\"name\":\"name1\"}," +
                        "{\"userId\":2,\"login\":\"login2\",\"name\":\"name2\"}]", MediaType.APPLICATION_JSON));

        User[] users = client.getUsers();
        assertEquals(2, users.length);
        assertNotNull(users[0]);
        assertNotNull(users[0].getUserId());
        assertEquals(new Long(1), users[0].getUserId());
        assertNotNull(users[0].getLogin());
        assertEquals("login1", users[0].getLogin());
        assertNotNull(users[0].getName());
        assertEquals("name1", users[0].getName());
        assertNotNull(users[1]);
        assertNotNull(users[1].getUserId());
        assertEquals(new Long(2), users[1].getUserId());
        assertNotNull(users[1].getLogin());
        assertEquals("login2", users[1].getLogin());
        assertNotNull(users[1].getName());
        assertEquals("name2", users[1].getName());
    }
 }
