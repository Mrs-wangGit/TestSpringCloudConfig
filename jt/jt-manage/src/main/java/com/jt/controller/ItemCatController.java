package com.jt.controller;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jt.annotation.Cache_Find;
import com.jt.pojo.ItemCat;
import com.jt.service.ItemCatService;
import com.jt.vo.EasyUITree;
@RequestMapping("/item")
@RestController
public class ItemCatController {
	@Autowired
	private ItemCatService ItemCatService;
	/*
	 * 根据商品分类id信息，查询商品分类的名称
	 * 
	 */
	@RequestMapping("/cat/queryItemName")
	public String findItemCatNameById(Long itemCatId) {

		ItemCat itemCat=	ItemCatService.findTemCatNameById(itemCatId  );
		return itemCat.getName();
	}

	/*
	 * 查询商品分类信息，进行树形结构展现
	 * 
	 */
	@RequestMapping("/cat/list")
	@Cache_Find
	public  List<EasyUITree> findItemCatTree(
			//required 是否是必填项 
			//name/value 客户端                                                                                                                                                                                                   提交过来的参数 重命名/默认值
			@RequestParam(name = "id",defaultValue = "0",required = true)
			Long parentId){
		//菜单表名  tb_item_cat 
		//当点击一级商品分类信息时，应当展现当前一级分类下的二级商品分类信息

		return ItemCatService.findItemCatTree(parentId);
	}
















}
