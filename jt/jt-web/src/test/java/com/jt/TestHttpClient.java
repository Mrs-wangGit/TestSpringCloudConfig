package com.jt;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestHttpClient {
	/**
	 * 测试HttpClient
	 * 步骤:
	 * 1.实例化请求的对象
	 * 2.确定URL的地址
	 * 3.定义请求的对象
	 * 4.发起请求,获取响应结果
	 * 5.200 成功 404 路径错误 406 服务器传回参数异常 500 服务器异常
	 *   400 客户端向服务器传递参数异常
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
 
	@Autowired
	private CloseableHttpClient httpClient;
	
	@Test
	public void testHttp() throws ClientProtocolException, IOException {
		//构建者模式获取对象

		String url="http://www.jt.com/";
		HttpGet httpGet = new HttpGet(url);
		
		//发起请求
		CloseableHttpResponse response = httpClient.execute(httpGet);
		if(200==response.getStatusLine().getStatusCode()) {
			System.out.println("success!");
			//表示请求执行正常
			HttpEntity entity = response.getEntity();
			String result = EntityUtils.toString(entity);
			System.out.println(result);
		}
		else {
			System.out.println("faile");
		}
		
		
	}
	@Test
	public void testHttpClient() throws ClientProtocolException, IOException {
		//构建者模式获取对象
		CloseableHttpClient client = HttpClients.createDefault();
		String url="http://www.jt.com/";
		HttpGet httpGet = new HttpGet(url);
		
		//发起请求
		CloseableHttpResponse response = client.execute(httpGet);
		if(200==response.getStatusLine().getStatusCode()) {
			System.out.println("success!");
			//表示请求执行正常
			HttpEntity entity = response.getEntity();
			String result = EntityUtils.toString(entity);
			System.out.println(result);
		}
		else {
			System.out.println("faile");
		}
		
		
	}

}
