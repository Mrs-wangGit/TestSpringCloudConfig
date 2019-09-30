package com.tedu.sp01.service;

import com.tedu.sp01.pojo.User;

public interface UserService {
	//根据用户id 获取用户信息
	User getUser(Integer id);
	//根据用户id 和指定的积分数 给用户加分
	void addScore(Integer id ,Integer score);

}
