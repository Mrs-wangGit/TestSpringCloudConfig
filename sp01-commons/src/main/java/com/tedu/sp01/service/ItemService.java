package com.tedu.sp01.service;

import java.util.List;

import com.tedu.sp01.pojo.Item;

public interface ItemService {

	//���ݶ�����id ��ȡ���е���Ʒ��Ϣ
	List<Item> getItems(String orderId);
	//���ݴ������Ʒ��Ϣ,������Ʒ�Ŀ��
	void decreaseNumbers(List<Item> list);
}
