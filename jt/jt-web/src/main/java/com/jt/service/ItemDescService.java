package com.jt.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.util.HttpClientService;

public interface ItemDescService {

 ItemDesc findItemDescById(Long id);


	
}
