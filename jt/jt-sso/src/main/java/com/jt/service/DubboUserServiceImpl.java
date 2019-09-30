package com.jt.service;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.mapper.CheckUserNameMapper;
import com.jt.pojo.User;
import com.jt.util.JtRedisUtil;

import redis.clients.jedis.JedisCluster;
@Service
public class DubboUserServiceImpl implements DubboUserService {
	
    private JtRedisUtil Mapper = JtRedisUtil.getJtRedisUtil();
	@Autowired
    private JedisCluster jedis;
	@Autowired
	private CheckUserNameMapper checkUserNameMapper;
	@Override
	public void saveUser(User user) {
		
		String md5Pass = 
				DigestUtils.md5DigestAsHex
				(user.getPassword().getBytes());
		user.setPassword(md5Pass)
			.setEmail(user.getPhone())
			.setCreated(new Date())
			.setUpdated(user.getCreated());
		checkUserNameMapper.insert(user);

	}
	@Override
	public String loginUser(User user) {
		String ticket=null;
		String md5Pass = 
				DigestUtils.md5DigestAsHex
				(user.getPassword().getBytes());
		user.setPassword(md5Pass);
		QueryWrapper<User> queryWrapper = new QueryWrapper<>(user);
		User selectOne = checkUserNameMapper.selectOne(queryWrapper);
		if(selectOne==null) {
		return null;}
		String uuid=UUID.randomUUID().toString();
		//对随机生成的uuid进行MD5加密
	    ticket=DigestUtils.md5DigestAsHex(uuid.getBytes());
	 
	    //去除敏感信息 设置虚假的密码
	    selectOne.setPassword("123456");
        String userJson=Mapper.toJson(selectOne);
        jedis.setex(ticket, 7*24*3600, userJson);
        System.out.println("写入redis成功");
        return ticket;
	}

	
}
