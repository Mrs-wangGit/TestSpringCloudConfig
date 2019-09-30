package com.jt.service;

import java.util.List;

import com.jt.pojo.ItemCat;
import com.jt.vo.EasyUITree;

public interface ItemCatService {

	ItemCat findTemCatNameById(Long itemCatId);

	List<EasyUITree> findItemCatTree(Long parentId);

	List<EasyUITree> findItemCatCache(Long parentId);

}
