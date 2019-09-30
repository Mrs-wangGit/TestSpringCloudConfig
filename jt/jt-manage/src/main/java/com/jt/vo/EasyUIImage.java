package com.jt.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class EasyUIImage {
	
	
private Integer error=0;//0表示正常 1表示失败
private String url;//网络访问的虚拟路径
private Integer width;
private Integer heigth;

}
