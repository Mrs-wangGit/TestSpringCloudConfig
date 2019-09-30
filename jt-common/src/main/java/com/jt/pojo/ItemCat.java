package com.jt.pojo;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain=true)
@TableName("tb_item_cat")
public class ItemCat extends BasePojo {
/**
	 * 
	 */
	private static final long serialVersionUID = -5236351620370375071L;
	@TableId(type=IdType.AUTO)
private Long id;//id
private Long parentId;//父级id
private String name;
private Integer status;
private Integer sortOrder;
private Boolean isParent;


}
