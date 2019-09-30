package com.jt.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jt.pojo.User;
import com.jt.service.DubboUserService;
import com.jt.util.JtRedisUtil;
import com.jt.vo.SysResult;

import redis.clients.jedis.JedisCluster;

@Controller
@RequestMapping("/user")
public class UserController {

	private static final String TICKET="JT_TICKET";
	@Reference(check = false)
	private DubboUserService userService;
	private JtRedisUtil Mapper = JtRedisUtil.getJtRedisUtil();
	@Autowired
	private JedisCluster jedis;
		
	@RequestMapping("/doRegister")
	@ResponseBody
	public SysResult register(User user) {

		userService.saveUser(user);
		return SysResult.success();

	}

	@RequestMapping("/doLogin")
	@ResponseBody
	public SysResult doLogin(User user,HttpServletResponse res) {
		//1.校验数据是否正确,获取密钥
		String ticket=userService.loginUser(user);
		if(StringUtils.isEmpty(ticket)) {
			return SysResult.fail();
		}else {
			Cookie cookie = new Cookie("JT_TICKET", ticket);
			cookie.setMaxAge(7*24*3600);
			cookie.setPath("/");// "/AA"只有在/AA路径下的url才能访问该cookie 默认为"/"
			cookie.setDomain("jt.com");//domain的设置是为了以后只有访问这个domian下的网址，才会把这个cookie项带上，这个在sso单点登录的时候比较常见
			res.addCookie(cookie);//将cookie写入客户端
			return SysResult.success();
		}


	}
	/**
	 * 1.获取cookie数据
	 * 2.删除redis
	 * 3.删除cookie
	 * @return
	 */
	@RequestMapping("/logout")
	public String logout(HttpServletRequest request,HttpServletResponse response) {

		Cookie[] cookies = request.getCookies();
		String ticket=null;
		if(cookies.length>0) {
			for (Cookie cookie : cookies) {
				String name = cookie.getName();
				if(TICKET.equals(name)) {
					ticket = cookie.getValue();
					break;
				}
			}
		}
		if(!StringUtils.isEmpty(ticket)){
			jedis.del(ticket);
			Cookie cookie = new Cookie(TICKET,"");
			cookie.setMaxAge(0);
			cookie.setPath("/");
			cookie.setDomain("jt.com");
			response.addCookie(cookie);
		}
		//重定向到系统首页
		return "redirect:/";

	}

	@RequestMapping("/{moduleName}")
	public String module(@PathVariable String moduleName) {
		return moduleName;

	}

}
