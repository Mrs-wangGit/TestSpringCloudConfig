package com.jt.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.mapper.CheckUserNameMapper;
import com.jt.pojo.User;
@Service
public class CheckUserNameServiceImpl implements CheckUserNameService {

	@Autowired
	private CheckUserNameMapper checkUserNameMapper;
	
	@Override
	public User findUserName(String userName) {
		QueryWrapper<User> q = new QueryWrapper<User>();
   	     q.eq("username", userName);
   	  User selectOne = checkUserNameMapper.selectOne(q);
		return selectOne;
	}

	@Override
	public User findUserPhone(String param) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User findUserEmail(String param) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean checkUser(String param, Integer type) {
		String column=type==1?"username":(type==2?"phone":"Emal");
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		 queryWrapper.eq(column, param);
		int count = checkUserNameMapper.selectCount(queryWrapper);
		return count>0?true:false;
	}

}
