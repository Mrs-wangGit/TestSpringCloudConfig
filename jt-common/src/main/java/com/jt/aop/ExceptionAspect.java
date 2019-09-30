package com.jt.aop;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.jt.vo.SysResult;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@ControllerAdvice//定义controller层的切面
public class ExceptionAspect {
	@ExceptionHandler(RuntimeException.class)
	
	public SysResult catchException(Exception exception) {
		exception.printStackTrace();
		log.error("异常信息：",exception);
		return SysResult.fail();
		
	}

}
