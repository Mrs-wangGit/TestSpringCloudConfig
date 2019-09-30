package com.jt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.pojo.ItemDesc;
import com.jt.util.JtRedisUtil;

public class TestObjectToJson {
private JtRedisUtil jt=JtRedisUtil.getJtRedisUtil();


@Test
public void toJson(){
	ItemDesc ic = new ItemDesc();
	ic.setItemId(100L);
	ic.setItemDesc("详情");
	String js = jt.toJson(ic);
	System.out.println(js);
	//json转化为对象
	ItemDesc rv = jt.toObject(js, ItemDesc.class);
	System.out.println(rv);
	
}
//List集合转化成json
@Test
@SuppressWarnings("unchecked")
public void testToList(){
	List<ItemDesc> list=new ArrayList();//向上转型，只能使用List接口中的方法
	ItemDesc itemDesc = new ItemDesc();
	itemDesc.setItemId(100L);
	itemDesc.setItemDesc("详情");
	ItemDesc itemDesc2=new ItemDesc();
	itemDesc2.setItemDesc("dsada");
	itemDesc2.setItemId(500L);
	list.add(itemDesc);
	list.add(itemDesc2);
	String writeValueAsString = jt.toJson(list);
	System.out.println(writeValueAsString);
  List <ItemDesc> readValue = jt.toObject(writeValueAsString, list.getClass());
  System.out.println(readValue);
}
}
