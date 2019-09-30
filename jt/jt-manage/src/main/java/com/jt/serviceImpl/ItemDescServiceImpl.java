package com.jt.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jt.mapper.ItemDescMapper;
import com.jt.pojo.ItemDesc;
import com.jt.service.ItemDescService;
@Service
public class ItemDescServiceImpl implements ItemDescService{
@Autowired
private ItemDescMapper itemDescMapper;
	@Override
	public ItemDesc queryItemDesc(Long ids) {
		ItemDesc selectById = itemDescMapper.selectById(ids);
		
		
	

		return selectById;
	}

}
