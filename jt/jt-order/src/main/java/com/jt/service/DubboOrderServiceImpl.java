package com.jt.service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.mapper.OrderItemMapper;
import com.jt.mapper.OrderMapper;
import com.jt.mapper.OrderShippingMapper;
import com.jt.pojo.Order;
import com.jt.pojo.OrderItem;
import com.jt.pojo.OrderShipping;

@Service
public class DubboOrderServiceImpl implements DubboOrderService{

	@Autowired
	private OrderMapper orderMapper;
	@Autowired
	private OrderShippingMapper oderShippingMapper;
	@Autowired
	private OrderItemMapper orderItemMapper;
	/**
	 * 1.准备orderId
	 * 2.分别完成入库操作
	 */
	@Override
	@Transactional
	public String saveOrder(Order order) {
		String orderId=null;
		//1.入库订单表
		String oldUuid=UUID.randomUUID().toString();
		String uuid = oldUuid.replace("-", "");
		order.setOrderId(uuid)
		     .setStatus(1)//表示未付款
		     .setCreated(new Date())
		     .setUpdated(new Date());
		orderMapper.insert(order);
		
		//2.入库订单物流
		OrderShipping shipping = order.getOrderShipping();
		shipping.setOrderId(uuid)
		        .setCreated(new Date())
		        .setUpdated(new Date());
		oderShippingMapper.insert(shipping);
		     
		//3.入库订单商品
		List<OrderItem> orderItems = order.getOrderItems();
		for (OrderItem orderItem : orderItems) {
			orderItem.setItemId(uuid)
			         .setCreated(new Date())
			         .setUpdated(new Date());
			orderItemMapper.insert(orderItem);
			         
		}
		return uuid;
	}
	@Override
	public Order findOrderById(String id) {
		/*
		 * Order order=orderMapper.selectById(id); OrderShipping orderShipping =
		 * oderShippingMapper.selectById(id); QueryWrapper<OrderItem> queryWrapper = new
		 * QueryWrapper<>(); queryWrapper.eq("order_id", id); List<OrderItem> orderItems
		 * = orderItemMapper.selectList(queryWrapper);
		 * order.setOrderShipping(orderShipping).setOrderItems(orderItems);
		 */
		Order order=orderMapper.findOrderById(id);
		
		return order;
	}
}
