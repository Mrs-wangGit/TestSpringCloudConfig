package com.jt.serviceImpl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.jt.mapper.ItemDescMapper;
import com.jt.mapper.ItemMapper;
import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.service.ItemService;
import com.jt.vo.EasyUITable;

@Service
public class ItemServiceImpl implements ItemService {
	

	@Autowired
	private ItemMapper itemMapper;
	@Autowired
	private ItemDescMapper itemDescMapper;

	@Override
	public EasyUITable fingItemByPage(Integer page, Integer rows) {
		//1.获取记录总数
		int total=itemMapper.selectCount(null);
	    //2.进行分页查询
		int start =(page-1) *rows;
		List<Item> list=itemMapper.findItemByPage(start, rows);
		return new EasyUITable(total,list);
	}
//保存商品时，保存商品的描述
	@Override
	public int saveItem(Item item,ItemDesc itemDesc) {
		item.setStatus(1);
		item.setCreated(new Date());
		item.setUpdated(item.getCreated());
		int insert = itemMapper.insert(item);
		//mybatis-plus可以直接获得item_id
		itemDesc.setItemId(item.getId())
		          .setUpdated(item.getUpdated())
		          .setCreated(item.getCreated());
		   itemDescMapper.insert(itemDesc);
		return insert;
	}
//修改商品时，更新商品的描述
	@Override
	public void updateItem(Item item,ItemDesc itemDesc) {
		item.setUpdated(new Date());
		itemMapper.updateById(item);
		ItemDesc setItemId = itemDesc.setItemId(item.getId());
        
         itemDescMapper.updateById(setItemId);
		
	}



//删除商品时，应该删除item和itemDesc
	@Override
	public void deleteItem(Long[] ids) {
		//循环遍历删除
		/*
		 * 与数据库连接次数过多。。。
		 * for (int i = 0; i < ids.length; i++) {
		 *  Long id=ids[i]; 
		 *  int deleteById=itemMapper.deleteById(id);}
		 * 
		 */
		//转换成集合删除
		List<Long> list=Arrays.asList(ids);
		itemMapper.deleteBatchIds(list);
		itemDescMapper.deleteBatchIds(list);
	
		
	}

	@Override
	public void updateStatus(int status, Long[] ids) {
		for (Long id : ids) {
			Item item = new Item();
			item.setId(id)
			.setStatus(status)
			.setUpdated(new Date());
			itemMapper.updateById(item);
			
		}

		
	}

	@Override
	public void updateReshelf(int status, Long[] ids) {
		for (Long id : ids) {
			Item item = new Item();
			item.setId(id)
			.setStatus(status)
			.setUpdated(new Date());
			itemMapper.updateById(item);
	}
	
	
	}
	@Override
	public Item fingItemById(Long id) {
		
		return itemMapper.selectById(id);
	}}
	
	
	
	

