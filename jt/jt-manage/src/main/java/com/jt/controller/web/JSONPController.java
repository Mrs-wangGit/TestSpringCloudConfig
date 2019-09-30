package com.jt.controller.web;

import java.util.Date;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.jt.pojo.ItemDesc;
import com.jt.util.JtRedisUtil;

@RestController
@RequestMapping()
public class JSONPController {

	
	@RequestMapping("/web/testJSONP")
	public JSONPObject testJsonP(String callback) {
		ItemDesc i = new ItemDesc();
		i.setCreated(new Date());
		i.setItemDesc("JSONP测试");

		return new JSONPObject(callback, i);
		
	}
}
