package com.jt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.jt.service.CheckUserNameService;
import com.jt.util.JtRedisUtil;
import com.jt.vo.SysResult;

import redis.clients.jedis.JedisCluster;

@RestController
public class UserController {
@Autowired
private	CheckUserNameService checkUserNameService;
/**
 * 说明:由于JSONP跨域请求，所以使用JSONPObject封装数据
 * 参数说明:
 *      1.param 表示用户校验的参数
 *      2.type  表示校验的字段 （1.username 2.phone 3.Email）
 * @param userName
 * @return
 */
@RequestMapping("/user/check/{param}/{type}")
public JSONPObject checkUser(@PathVariable String param,
		@PathVariable Integer type,  String callback) {
	System.out.println(callback);
	boolean flag=checkUserNameService.checkUser(param,type);
	return new JSONPObject(callback,SysResult.success("ok", flag));
	
}
/**
 * GET http://sso.jt.com/user/query/cc599f606e89061929b4784930e16e6c?callback=jsonp1568604754089&_=1568604754142 
 */
@Autowired
private JedisCluster jedis;
private JtRedisUtil Mapper = JtRedisUtil.getJtRedisUtil();
@RequestMapping("/user/query/{ticket}")
public JSONPObject findUserByTicket(@PathVariable String ticket ,String callback) {
	//1.利用ticket获取数据
	String userJson = jedis.get(ticket);
	
	return new JSONPObject(callback,SysResult.success(userJson));
	
}

	/*
	 * @RequestMapping("/user/check/{param}/{type}") public JSONPObject
	 * checkUserName(@PathVariable String param,
	 * 
	 * @PathVariable Integer type, String callback) { User user=null; if(type==1) {
	 * user = checkUserNameService.findUserName(param);} else if(type==2) { user =
	 * checkUserNameService.findUserPhone(param); }else if(type==3) { user =
	 * checkUserNameService.findUserEmail(param); } SysResult sysResult = new
	 * SysResult(); if(user!=null) { sysResult.setData(true);
	 * sysResult.setMsg("ok"); sysResult.setStatus(200); }else {
	 * sysResult.setData(false); sysResult.setMsg("fail"); sysResult.setStatus(201);
	 * } JSONPObject jsonpObject = new JSONPObject(callback, sysResult); return
	 * jsonpObject;
	 * 
	 * }
	 */
	
	
}
