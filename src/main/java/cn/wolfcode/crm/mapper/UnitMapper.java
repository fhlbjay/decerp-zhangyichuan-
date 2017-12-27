package cn.wolfcode.crm.mapper;

import cn.wolfcode.crm.domain.Unit;

import java.util.List;

public interface UnitMapper {
	void deleteByPrimaryKey(Long id);

	void insert(Unit record);

	Unit selectByPrimaryKey(Long id);

	List<Unit> selectAll();

	void updateByPrimaryKey(Unit record);

	Unit selectUnitByName(String name);
}