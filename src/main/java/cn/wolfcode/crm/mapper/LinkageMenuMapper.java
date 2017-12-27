package cn.wolfcode.crm.mapper;

import cn.wolfcode.crm.domain.LinkageMenu;
import cn.wolfcode.crm.query.QueryObject;

import java.util.List;

public interface LinkageMenuMapper {
	void deleteByPrimaryKey(Long id);

	void insert(LinkageMenu record);

	LinkageMenu selectByPrimaryKey(Long id);

	List<LinkageMenu> selectAll();

	void updateByPrimaryKey(LinkageMenu record);

	LinkageMenu seleRoot(String name);

	int queryCount(QueryObject qo);

	List<LinkageMenu> queryList(QueryObject qo);

	void deleteDirnameByRootId(Long id);
}