package com.jt.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jt.annotation.Cache_Find;
import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.service.ItemDescService;
import com.jt.util.HttpClientService;
import com.jt.util.JtRedisUtil;

@Service
public class ItemDescServiceImpl implements ItemDescService {


	private JtRedisUtil jtJedisMapper=JtRedisUtil.getJtRedisUtil();
	@Autowired
	private HttpClientService httpClientService;
	@Override
	@Cache_Find
	public ItemDesc findItemDescById(Long id) {
		String url="http://manage.jt.com/web/item/findItemDescById/"+id;
		String ItemJson=httpClientService.doGet(url);
		return   jtJedisMapper.toObject(ItemJson, ItemDesc.class);

}
}
