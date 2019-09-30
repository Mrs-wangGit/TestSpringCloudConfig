package com.jt.service;

import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.vo.EasyUITable;

public interface ItemService {

	EasyUITable fingItemByPage(Integer page, Integer rows);

	int saveItem(Item item,ItemDesc itemDesc);

	void updateItem(Item item,ItemDesc itemDesc);

 void deleteItem(Long[] ids);

void updateStatus(int status, Long[] ids);

void updateReshelf(int status, Long[] ids);

Item fingItemById(Long id);
	
}
