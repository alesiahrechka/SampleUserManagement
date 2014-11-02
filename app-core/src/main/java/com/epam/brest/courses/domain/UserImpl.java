package com.epam.brest.courses.domain;

/**
 * Created by alesya on 31.10.14.
 */
public class UserImpl implements User{
    private Long userId;

    private String login;

    private String name;


    public UserImpl(Long userId, String login, String name){
        this.userId = userId;
        this.login = login;
        this.name = name;
    }

    public UserImpl(){

    }

    @Override
    public void setUserId(Long userId){
        this.userId = userId;
    }

    @Override
    public Long getUserId(){
        return userId;
    }

    @Override
    public void setLogin(String login){
        this.login = login;
    }

    @Override
    public String getLogin(){
        return login;
    }

    @Override
    public void setName(String name){
        this.name = name;
    }

    @Override
    public String getName(){
        return name;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", login='" + login + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

}
