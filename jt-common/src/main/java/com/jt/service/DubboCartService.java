package com.jt.service;

import java.util.List;

import com.jt.pojo.Cart;
import com.jt.pojo.User;

public interface DubboCartService {

	List<Cart> findCartListByUserId(Long userId);

	void updateCartNum(Cart cart);

	void deleteCart(Cart cart);

	void saveCart(Cart cart);



	//定义dubbo服务接口
}
