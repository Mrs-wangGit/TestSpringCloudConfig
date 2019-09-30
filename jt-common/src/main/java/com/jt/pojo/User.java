package com.jt.pojo;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.experimental.Accessors;

@TableName("tb_user")
@Data
@Accessors(chain = true)
public class User extends BasePojo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;                   
	private String  username;           
	private String   password;            
	private String  phone;               
	private String email;               

}
