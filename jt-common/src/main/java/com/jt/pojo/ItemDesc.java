package com.jt.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain=true)
@TableName("tb_item_desc")
@AllArgsConstructor
@NoArgsConstructor
public class ItemDesc extends BasePojo {
	@TableId //标识主键
private Long itemId;//表示商品id号
private String itemDesc;//商品详情
}
