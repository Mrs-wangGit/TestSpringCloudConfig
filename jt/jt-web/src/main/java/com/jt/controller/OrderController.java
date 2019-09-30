package com.jt.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jt.pojo.Cart;
import com.jt.pojo.Order;
import com.jt.service.DubboCartService;
import com.jt.service.DubboOrderService;
import com.jt.util.UserThreadLocalUtil;
import com.jt.vo.SysResult;

@Controller
@RequestMapping("/order")
public class OrderController {

	@Reference(check=false) 
	private DubboCartService dubboCartService;
	@Reference(check=false) 
	private DubboOrderService dubboOrderService;
	//http://www.jt.com/order/create.html 实现结算订单页面
	@RequestMapping("/create")
	public String create(Model model) {
		//拦截器生效时,才会在ThreadLocal中存入userId
		Long userId = UserThreadLocalUtil.get().getId();
		List<Cart> cartList = dubboCartService.findCartListByUserId(userId);
		model.addAttribute("carts", cartList);
		return "order-cart";
	}
	/**
	 * 根据页面传参,实现订单入库操作 三张表:订单 物流 商品
	 * 之后将订单编号 orderId返回,为了查询订单信息提供数据的支持
	 * @param order
	 * @return
	 */
	@RequestMapping("/submit")
	@ResponseBody
	public SysResult saveOrder(Order order) {
		
	String orderId=	dubboOrderService.saveOrder(order);
		
		return SysResult.success(orderId) ;
		
	}
	@RequestMapping("/success")
	public String findOrderById(String id,Model model) {
	Order order=	dubboOrderService.findOrderById(id);
		model.addAttribute(order);
		return "success";
		
	}
}
