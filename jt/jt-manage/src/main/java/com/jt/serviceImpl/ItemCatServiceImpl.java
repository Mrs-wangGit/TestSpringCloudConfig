package com.jt.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.mapper.ItemCatMapper;
import com.jt.pojo.ItemCat;
import com.jt.service.ItemCatService;
import com.jt.util.JtRedisUtil;
import com.jt.vo.EasyUITree;

import redis.clients.jedis.Jedis;

@Service
public class ItemCatServiceImpl implements ItemCatService {
     @Autowired
	private ItemCatMapper itemCatMapper;
   // @Autowired
    private Jedis jedis;
    
 
	@Override
	public ItemCat findTemCatNameById(Long itemCatId) {
		ItemCat selectById = itemCatMapper.selectById(itemCatId);
		return selectById;
	}
	
	
	@Override
	public List<EasyUITree> findItemCatTree(Long parentId) {
		
	    List<EasyUITree> treeList=new ArrayList<EasyUITree>();
	    
		List<ItemCat> list = this.findItemCat(parentId);
		for (ItemCat itemCat : list) {
			EasyUITree e = new EasyUITree();
			String state=itemCat.getIsParent()?"closed":"open";
			e.setId(itemCat.getId())
			  .setText(itemCat.getName())
			  .setState(state);
			treeList.add(e);
		}
		
		return treeList;
	}
	
    public List<ItemCat> findItemCat(Long parentId){
    	QueryWrapper<ItemCat> queryWrapper = new QueryWrapper<>();
    	 queryWrapper.eq("parent_id", parentId);
    	List<ItemCat> list = itemCatMapper.selectList(queryWrapper);
    	return list;
	}

    
    private JtRedisUtil jtJedisMapper=JtRedisUtil.getJtRedisUtil();
	@Override
	public List<EasyUITree> findItemCatCache(Long parentId) {
		List<EasyUITree> treeList=new ArrayList<>();
		String key="ITEM_CAT::"+parentId;
		String result = jedis.get(key);
		//检查是否有数据
		if(StringUtils.isEmpty(result)) {
			 treeList=findItemCatTree(parentId);
			String json = jtJedisMapper.toJson(treeList);
			jedis.set(key, json);
			System.out.println("第一次查询数据库");
		}else {
			System.out.println("从缓存获取数据");
			//缓存中有数据
			treeList= jtJedisMapper.toObject(result,treeList.getClass() );
		}
		return treeList;
	}
}
