package com.jt.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController {
	/*
	 * 语法规则：springMVC动态获取url中的数据
	 * 1.参数必须使用{}包裹
	 * 2.参数域参数之间用/分割，参数位置固定
	 * 3.利用@pathVariable注解接收，并且名称之一
	 * restFul风格
	 * 1.动态获取用户的参数。
	 * 2.请求路径是不变的，根据请求方式的不同实现CRUD操作
	 */
	@RequestMapping("/page/{moduleName}")
	public String module(@PathVariable String moduleName) {
		
		return moduleName;
	}
	@Autowired
	private Environment env;
	
	
	@RequestMapping("/getMsg")
	@ResponseBody
	public String getMsg() {
		String port=env.getProperty("server.port");
		return "我是"+port;
	}
}
