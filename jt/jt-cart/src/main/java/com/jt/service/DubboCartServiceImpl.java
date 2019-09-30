package com.jt.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.jt.mapper.CartMapper;
import com.jt.pojo.Cart;

@Service
public class DubboCartServiceImpl implements DubboCartService {

	@Autowired
	private CartMapper cartMapper;
	@Override
	public List<Cart> findCartListByUserId(Long userId) {
		
		QueryWrapper<Cart> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("user_Id", userId);
		List<Cart> selectList = cartMapper.selectList(queryWrapper);
		return selectList;
	}
	// update tb_cart set num=#{num},updated=now()   where user_id=#{user_id} and item_id=#{item_id}
	@Override
	public void updateCartNum(Cart cart) {
	  
		Cart cartTemp = new Cart();
		cartTemp.setNum(cart.getNum())
		         .setUpdated(new Date());
		UpdateWrapper<Cart> updateWrapper = new UpdateWrapper<>();
		updateWrapper.eq("item_id", cart.getItemId())
		             .eq("user_id", cart.getUserId());
		cartMapper.update(cartTemp, updateWrapper);//要修改的数据 +修改的条件
		
	}
	@Override
	public void deleteCart(Cart cart) {

		//要求对象当中 属性不为空的元素当做where条件
		QueryWrapper<Cart> queryWrapper = new QueryWrapper<>(cart);
		cartMapper.delete(queryWrapper);
	}
	/**
	 * 1.如果购物车列表有该数据,则做数量修改
	 * 2.如果购物车中没有该数据,做数据的新增
	 */
	@Override
	public void saveCart(Cart cart) {

		Cart cartTemp = new Cart();
		cartTemp.setUserId(cart.getUserId())
		        .setItemId(cart.getItemId());
		
		QueryWrapper<Cart> queryWrapper = new QueryWrapper<>(cartTemp);
		Cart cartDB = cartMapper.selectOne(queryWrapper);
		if(cartDB==null) {
			cart.setCreated(new Date())
			    .setUpdated(cart.getCreated());
			cartMapper.insert(cart);
		}else {
			//只修改数量
			int num=cart.getNum()+cartDB.getNum();
			cartDB.setNum(num)
			      .setUpdated(new Date());
			//主键充当where条件,其他数据当做要修改的数据
			//cartMapper.updateById(cartDB);
			cartMapper.updateCart(cartDB);
		}
	}
	

}
