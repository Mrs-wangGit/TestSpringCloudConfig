package com.jt.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.experimental.Accessors;

@TableName("tb_cart")
@Data
@Accessors(chain =true)
public class Cart extends BasePojo{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8209689274704358116L;
	/**
	 * 
	 */

	@TableId(type = IdType.AUTO)//设置主键自增
	private Long id;                  
	private Long userId;             
	private Long itemId;             
	private String itemTitle;         
	private String  itemImage;         
	private Long  itemPrice;         
	private Integer num;                


}
