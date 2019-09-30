package com.jt.controller.web;

import org.aspectj.internal.lang.annotation.ajcDeclareAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.service.ItemDescService;
import com.jt.service.ItemService;

/**
 * 要求从后台服务器返回jason数据
 * @author tarena
 * url:manage.jt.com/web/item/findItemById/562379
 */
@RestController
@RequestMapping("/web/item")
public class WebItemController {

	@Autowired
	private ItemService itemService;
	@Autowired
	private ItemDescService itemDescService;
	@RequestMapping("/findItemById/{id}")
	public Item findItemById(@PathVariable Long id) {
		return itemService.fingItemById(id);
		
	}
	@RequestMapping("/findItemDescById/{id}")
	public ItemDesc findItemDescById(@PathVariable Long id) {
		return itemDescService.queryItemDesc(id);
		
	}
}
