package com.tedu.sp01.service;

import java.util.List;

import com.tedu.sp01.pojo.Item;

public interface ItemService {

	//根据订单的id 获取其中的商品信息
	List<Item> getItems(String orderId);
	//根据传入的商品信息,减少商品的库存
	void decreaseNumbers(List<Item> list);
}
