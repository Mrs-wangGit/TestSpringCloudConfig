package com.jt;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.jt.util.HttpClientService;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestHttpClientService {

	@Autowired
	private HttpClientService httpClientService;
	
	@Test
	public void testHttpClientService() {
		Map<String ,String> params=new HashMap();
		params.put("1", "2");
		String url="http://www.jt.com/";
		String doGet = httpClientService.doGet(url,params); 
		System.out.println(doGet);
		System.out.println(params);
		
	}
}
