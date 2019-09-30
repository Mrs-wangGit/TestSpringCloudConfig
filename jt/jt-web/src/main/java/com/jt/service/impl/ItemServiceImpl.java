package com.jt.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jt.annotation.Cache_Find;
import com.jt.pojo.Item;
import com.jt.service.ItemService;
import com.jt.util.HttpClientService;
import com.jt.util.JtRedisUtil;
@Service
public class ItemServiceImpl implements ItemService {

	   private JtRedisUtil jtJedisMapper=JtRedisUtil.getJtRedisUtil();
	@Autowired
	private HttpClientService httpClientService;
	@Override
	@Cache_Find
	public Item findItemById(Long id) {
		String url="http://manage.jt.com/web/item/findItemById/"+id;
		String ItemJson=httpClientService.doGet(url);
		return (Item) jtJedisMapper.toObject(ItemJson, Item.class);
	}

}
