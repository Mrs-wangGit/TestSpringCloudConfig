package com.jt.util;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.pojo.ItemDesc;

import redis.clients.jedis.Jedis;

public class JtRedisUtil {
	private static JtRedisUtil jt;
    private static final ObjectMapper MAPPER=new ObjectMapper();


    public static synchronized JtRedisUtil getJtRedisUtil() {
    	 
    	if(jt==null) {
    	jt= new JtRedisUtil();}
    	return jt;
    }
public  String toJson(Object data) {
	String json=null;
	try {
		 json = MAPPER.writeValueAsString(data);
	} catch (JsonProcessingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		throw  new RuntimeException();
	}
	return json;
	
}

public <T> T toObject(String json,Class<T> Class) {
	T readValue=null;
		
			 try {
				readValue = MAPPER.readValue(json, Class);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new RuntimeException();
			}
	

	return readValue;
	
	
	
	
	
	
	
}
}
