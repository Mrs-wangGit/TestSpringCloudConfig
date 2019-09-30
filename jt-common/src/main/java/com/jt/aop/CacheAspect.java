package com.jt.aop;

import java.util.LinkedHashMap;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.jt.annotation.Cache_Find;
import com.jt.util.JtRedisUtil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.ShardedJedis;

@Aspect//标识切面
@Component//交给spring容器管理
public class CacheAspect {

	@Autowired(required = false)
	private JedisCluster jedis;

	/**
	 * 1.动态获取注解对象
	 * 2.根据key查询redis
	 * 2.1.如果没有数据,需要让目标方法执行,查询的结果保存到redis
	 * 3.将json串转化为返回值对象,返回
	 * @param joinPoint
	 * @param cacheFind
	 * @return
	 */
	
    private JtRedisUtil jtJedisMapper=JtRedisUtil.getJtRedisUtil();
	@SuppressWarnings("unchecked")
	@Around("@annotation(cacheFind)")
	public Object around(ProceedingJoinPoint joinPoint,Cache_Find cacheFind) {
        
		new LinkedHashMap<>();
		Object data=null;
		String  key=getKey(joinPoint,cacheFind);
		//1.从jedis中获取数据
		String result = jedis.get(key);
		//2.判断缓存中是否有数据
		try {
			if(StringUtils.isEmpty(result)) {
				//3.如果没有数据,就让目标方法执行,从数据库获取数据
				 data= joinPoint.proceed();	
				 //4.将此结果转化json
				 String json = jtJedisMapper.toJson(data);
				 //5.判断用户是否有编辑时间,如果有时间,必须设定超时时间
				 if(cacheFind.seconds()>0) {
					 jedis.setex(key, cacheFind.seconds(), json);
				 } else {
					 jedis.set(key,json);
				 }
				System.out.println("AOP从数据库中查询数据");
			}else {
				//缓存数据不为空
				//data= jtJedisMapper.toObject(result,Object.class );
				MethodSignature s=	(MethodSignature)joinPoint.getSignature();
				Class returnType = s.getReturnType();
				data= jtJedisMapper.toObject(result,returnType);
				System.out.println("AOP从缓存中获取数据");
			}
		
		}catch(Throwable e){
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		return data;
	}
	/**
	 * 动态获取key
	 * @param joinPoint
	 * @param cacheFind
	 * @return
	 */
	private String getKey(ProceedingJoinPoint joinPoint, Cache_Find cacheFind) {
		String key=cacheFind.key();
		if(StringUtils.isEmpty(key)) {
			String methodName	=joinPoint.getSignature().getName();
			//key自动生成
			String className=joinPoint.getSignature().getDeclaringTypeName();
			if(joinPoint.getArgs().length>0)
			{key=className+"."+methodName+"::"+joinPoint.getArgs()[0];}
			else {
				key=className+"."+methodName;
			}
			return key;
		}else {
			//key使用用户的 
			return key;
		}

	}

}
