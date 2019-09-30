package com.jt.service;

import com.jt.pojo.User;

public interface CheckUserNameService {

	public User findUserName(String userName);

	public User findUserPhone(String param);

	public User findUserEmail(String param);
//param:表示用户需要校验的数据
	//type:校验的类型
	public boolean checkUser(String param, Integer type);

}
