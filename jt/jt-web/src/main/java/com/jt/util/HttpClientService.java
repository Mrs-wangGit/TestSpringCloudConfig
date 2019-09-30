package com.jt.util;
/**
 * 封装HttpClient的工具API
 * @author tarena
 *
 */

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class HttpClientService {

	@Autowired
	private CloseableHttpClient httpClient;
	@Autowired
	private RequestConfig  requestConfig;
	/**
	 * 1.用户提交一个url 并调用工具类
	 * 2.由此工具类 返回Json串数据(服务器数据)给用户
	 * 参数：
	 *    
	 *    url:用户通过工具API访问的网址
	 *    charSet:定义字符集编码
	 *    params:利用Map集合封装数据
	 *    
	 *    
	 */
	public String doGet(String url) {
		return doGet(url,null,null);
		
	}
	public String doGet(String url,Map<String,String> params) {
		return doGet(url,params,null);
		
	}
	public String doGet(String url,Map<String,String> params ,String charset) {
		//1.判断字符集编码是否为空 如果为空则给定默认值utf-8
		if(StringUtils.isEmpty(charset)){
			charset = "UTF-8";}
		url+="?";
		if(params!=null) {
		Set<Entry<String, String>> entrySet = params.entrySet();
		Iterator<Entry<String, String>> iterator = entrySet.iterator();
		while(iterator.hasNext()) {
			Entry<String, String> next = iterator.next();
			String key = next.getKey();
			String value = next.getValue();
			url+=key+"="+value+"&";
		}
		url=url.substring(0, url.length()-1);
		}
		
		//3.发起请求
		HttpGet httpGet = new HttpGet(url);
		httpGet.setConfig(requestConfig);//设定请求超时时间
		String result=null;
		try {
			CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
			//校验用户请求是否正确
			if(200==httpResponse.getStatusLine().getStatusCode()) {
				result=EntityUtils.toString(httpResponse.getEntity());
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
			}
		return result; 
	}

}
