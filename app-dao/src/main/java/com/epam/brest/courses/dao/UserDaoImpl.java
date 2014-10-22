package com.epam.brest.courses.dao;

import com.epam.brest.courses.domain.User;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;
/**
 * Created by alesya on 21.10.14.
 */
public class UserDaoImpl implements UserDao {

    private JdbcTemplate jdbctemplate;

    public void setDataSource(DataSource dataSource) {
        jdbctemplate = new JdbcTemplate(dataSource);
    }


    @Override
    public void addUser(User user) {
    }

    @Override
    public List<User> getUsers() {
        return null;
    }

    @Override
    public void removeUser(Long userId) {

    }
}
