package cn.wolfcode.crm.service;

import cn.wolfcode.crm.domain.LinkageMenu;
import cn.wolfcode.crm.query.PageResult;
import cn.wolfcode.crm.query.QueryObject;

import java.util.List;

public interface ILinkageMenuService {
	void deleteByPrimaryKey(Long id);

	void insert(LinkageMenu record);

	LinkageMenu selectByPrimaryKey(Long id);

	List<LinkageMenu> selectAll();

	LinkageMenu seleRoot(String name);

	void updateByPrimaryKey(LinkageMenu record);

	PageResult query(QueryObject qo);
}
