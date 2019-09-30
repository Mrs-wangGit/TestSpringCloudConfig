package com.jt.interceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import com.jt.pojo.User;
import com.jt.util.JtRedisUtil;
import com.jt.util.UserThreadLocalUtil;

import redis.clients.jedis.JedisCluster;


//检查用户是否登录的拦截器
@Component
public class UserInterceptor implements HandlerInterceptor{

	private static JtRedisUtil Mapper = JtRedisUtil.getJtRedisUtil();
	private static final String TICKET="JT_TICKET";
	@Autowired
	private JedisCluster jedisCluster;
	/**
	 * 实现用户权限认证
	 *  用户不登录,不允许访问涉密操作,重定向到用户登录页面
	 *  用户已登录,则请求放行
	 *方法说明:
	 *  boolean: true 表示放行 false:表示拦截 配合重定向使用
	 *实现步骤:
	 *   1.获取用户的cookie信息 获取密钥
	 *   2.从redis中获取数据
	 *   3.后续所有操作涉密操作都会被拦截
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

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
		if(!StringUtils.isEmpty(ticket)) {
			//校验redis中是否有数据
			String userJson = jedisCluster.get(ticket);
			if(!StringUtils.isEmpty(userJson)) {
				User user = Mapper.toObject(userJson, User.class);
				request.setAttribute("JT_USER", user);
				UserThreadLocalUtil.set(user);
				System.out.println(Thread.currentThread().getName());
				return true;
			}
		}
		//如果用户没有登录,重定向到首页
		response.sendRedirect("/user/login.html");
		return false;
	}
@Override
public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
		throws Exception {
	//防止内存溢出
			UserThreadLocalUtil.remove();

}

}
