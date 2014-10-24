package com.epam.brest.courses.domain;

public class User{

	private Long userId;
	
	private String login;

	private String name;

    public User(){
        this.userId = -1L;
        this.login = "";
        this.name = "";
    }

    public User(Long userId,String login,String name){
        this.userId = userId;
        this.login = login;
        this.name = name;
    }

	public void setUserId(Long userId){
		this.userId = userId;
	}

	public Long getUserId(){
		return userId;
	}

	public void setLogin(String login){
		this.login = login;
	}

	public String getLogin(){
		return login;
	}

	public void setName(String name){
		this.name = name;
	}

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



