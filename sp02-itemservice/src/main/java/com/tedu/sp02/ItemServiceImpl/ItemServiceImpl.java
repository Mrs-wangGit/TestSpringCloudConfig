package com.tedu.sp02.ItemServiceImpl;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.tedu.sp01.pojo.Item;
import com.tedu.sp01.service.ItemService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ItemServiceImpl implements ItemService{

	@Override
	public List<Item> getItems(String orderId) {
		ArrayList<Item> list = new ArrayList<Item>();
		list.add(new Item(1,"商品 1",1));
		list.add(new Item(2,"商品 2",2));
		list.add(new Item(3,"商品 1",3));
		list.add(new Item(14,"商品 1",4));
		return list;
	}

	@Override
	public void decreaseNumbers(List<Item> list) {
		if(log.isInfoEnabled()) {
			for (Item item : list) {
				log.info("减少库存-"+item);
			}
		}
		
	}
	
}