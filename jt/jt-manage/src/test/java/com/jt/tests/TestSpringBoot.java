package com.jt.tests;


import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.omg.CORBA.PRIVATE_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import com.jt.mapper.ItemMapper;
import com.jt.pojo.Item;
import com.jt.service.ItemService;


@SpringBootTest
@RunWith(SpringRunner.class)
public class TestSpringBoot {
	@Autowired
	private ItemMapper itemMapper;
	@Autowired
    private  ItemService itemService;
    @Test
	public void find() {
		List<Item> findItemByPage = itemMapper.findItemByPage(0,20);
		for (Item item : findItemByPage) {
			Long id = item.getId();
			System.out.println(id);
			System.out.println(itemMapper.getClass().getName());
			System.out.println(itemService.getClass().getName());
		}
	
	}
}
