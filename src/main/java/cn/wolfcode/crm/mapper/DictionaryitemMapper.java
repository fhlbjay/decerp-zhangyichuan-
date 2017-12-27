package cn.wolfcode.crm.mapper;

import cn.wolfcode.crm.domain.Dictionaryitem;

import java.util.List;

public interface DictionaryitemMapper {
	void deleteByPrimaryKey(Long id);

	void insert(Dictionaryitem record);

	void updateByPrimaryKey(Dictionaryitem record);

	int queryCount(String sn);

	List<Dictionaryitem> queryList(String sn);

	void deleteDictionaryitemByDictionaryId(Long id);
}