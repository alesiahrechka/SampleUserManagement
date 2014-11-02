package com.epam.brest.courses.dao;

import com.epam.brest.courses.domain.User;
import com.epam.brest.courses.domain.UserImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.util.Assert;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by alesya on 21.10.14.
 */
public class UserDaoImpl implements UserDao {

    @Value("#{T(org.apache.commons.io.FileUtils).readFileToString((new org.springframework.core.io.ClassPathResource('${insert_into_user_path}')).file)}")
    public String addNewUserSql;

    @Value("#{T(org.apache.commons.io.FileUtils).readFileToString((new org.springframework.core.io.ClassPathResource('${delete_from_user_path}')).file)}")
    public String deleteFromUserSql;

    @Value("#{T(org.apache.commons.io.FileUtils).readFileToString((new org.springframework.core.io.ClassPathResource('${update_user_path}')).file)}")
    public String updateUserSql;

    @Value("#{T(org.apache.commons.io.FileUtils).readFileToString((new org.springframework.core.io.ClassPathResource('${select_all_users_path}')).file)}")
    public String selectAllUsersSql;

    @Value("#{T(org.apache.commons.io.FileUtils).readFileToString((new org.springframework.core.io.ClassPathResource('${select_user_by_id_path}')).file)}")
    public String selectUserByIdSql;

    @Value("#{T(org.apache.commons.io.FileUtils).readFileToString((new org.springframework.core.io.ClassPathResource('${select_user_by_login_path}')).file)}")
    public String selectUserByLoginSql;

    public static final String USER_ID = "userid";
    public static final String LOGIN = "login";
    public static final String NAME = "name";

    private static final Logger LOGGER = LogManager.getLogger();

    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedJdbcTemplate;

    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        namedJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public int addUser(User user) {
        LOGGER.debug("addUser({}) ", user);
        Assert.notNull(user);
        Assert.isNull(user.getUserId());
        Assert.notNull(user.getLogin(), "User login should be specified.");
        Assert.notNull(user.getName(), "User name should be specified.");

        KeyHolder keyholder = new GeneratedKeyHolder();

        SqlParameterSource namedParameters;
        namedParameters = new MapSqlParameterSource()
                .addValue(NAME, user.getName())
                .addValue(LOGIN, user.getLogin())
                .addValue(USER_ID, user.getUserId());
        namedJdbcTemplate.update(addNewUserSql, namedParameters, keyholder);
        LOGGER.debug("addUser(): id{}",keyholder.getKey());

        return  keyholder.getKey().intValue();
    }

    @Override
    public List<User> getUsers() {
        LOGGER.debug("get users()");
        return jdbcTemplate.query(selectAllUsersSql, new UserMapper());
    }

    @Override
    public void removeUser(Long userId) {
        LOGGER.debug("removeUser(userId={}) ", userId);
        jdbcTemplate.update(deleteFromUserSql, userId);
    }

    @Override
    public User getUserByLogin(String login) {
        LOGGER.debug("getUserByLogin(login={})", login);
        return jdbcTemplate.queryForObject(selectUserByLoginSql,
                new String[]{login.toLowerCase()}, new UserMapper());
    }

    @Override
    public User getUserById(long userId) {
        LOGGER.debug("getUserById(userId={})", userId);
        return jdbcTemplate.queryForObject(selectUserByIdSql,
                new UserMapper(), userId);
    }

    @Override
    public void updateUser(User user) {
        LOGGER.debug("updateUser({}).. ", user);

        Map<String, Object> parameters = new HashMap(3);
        parameters.put(NAME, user.getName());
        parameters.put(LOGIN, user.getLogin());
        parameters.put(USER_ID, user.getUserId());
        namedJdbcTemplate.update(updateUserSql, parameters);

    }

    @Override
    public User getUserInstance() {
        return new UserImpl();
    }

    public class UserMapper implements RowMapper<User> {

        public User mapRow(ResultSet rs, int i) throws SQLException {
            User user = getUserInstance();
            user.setUserId(rs.getLong(USER_ID));
            user.setLogin(rs.getString(LOGIN));
            user.setName(rs.getString(NAME));
            return user;
        }
    }
}
