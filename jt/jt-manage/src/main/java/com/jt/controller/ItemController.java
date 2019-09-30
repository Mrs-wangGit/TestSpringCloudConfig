package com.jt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.service.ItemDescService;
import com.jt.service.ItemService;
import com.jt.vo.EasyUITable;
import com.jt.vo.SysResult;

@RestController
@RequestMapping("/item")
public class ItemController {
	
	@Autowired
	private ItemService itemService;
	@Autowired
	private ItemDescService itemServiceDesc;
  /*
        * 展示商品列表数据
   */
	@RequestMapping("/query")
	public EasyUITable fingItemByPage(Integer page,Integer rows) {
		
		System.out.println(itemService.getClass().getName());
		System.out.println(itemServiceDesc.getClass().getName());
		return itemService.fingItemByPage(page,rows);
		
	}
	
	/*try {
		int rows=itemService.saveItem();
		return SysResult.success();
		}
		catch (Exception e) {
		return SysResult.fail();
	}
	 * 
	 */
//实现商品的新增 +商品的描述
	@RequestMapping("/save")
	
	public SysResult saveItem(Item item ,ItemDesc itemDesc) {
		
		itemService.saveItem(item,itemDesc);
		return SysResult.success();
		
	}
	@RequestMapping("/update")
	public SysResult updateItem(Item item,ItemDesc itemDesc) {
		itemService.updateItem(item,itemDesc);
		return SysResult.success();
		
	}
	//实现商品的删除 var params={"ids":ids};
	
	@RequestMapping("/{moduleName}")
	public SysResult instockItem(@PathVariable String moduleName,Long[]ids) {
		if("delete".equals(moduleName)) {

			itemService.deleteItem(ids);
		}
		
		else if("instock".equals(moduleName)) {
			int status=2;
			itemService.updateStatus(status,ids);
			
		}
		else if("reshelf".equals(moduleName)) {
			int status=1;
			itemService.updateReshelf(status,ids);
		}
			
			return SysResult.success();
		

	}
	@RequestMapping("/query/item/desc/{ids}")
	public SysResult queryItemDesc(@PathVariable(name = "ids")Long ids) {
		ItemDesc itemDesc = itemServiceDesc.queryItemDesc(ids);
		
		return SysResult.success(null, itemDesc);
	}
	
}
