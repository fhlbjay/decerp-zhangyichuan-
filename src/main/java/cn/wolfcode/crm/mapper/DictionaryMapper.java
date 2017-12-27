package cn.wolfcode.crm.mapper;

import cn.wolfcode.crm.domain.Dictionary;
import cn.wolfcode.crm.query.QueryObject;

import java.util.List;

public interface DictionaryMapper {
    void deleteByPrimaryKey(Long id);

    void insert(Dictionary record);

    Dictionary selectByPrimaryKey(Long id);

    List<Dictionary> selectAll();

   void updateByPrimaryKey(Dictionary record);

	int queryCount(QueryObject qo);

	List<Dictionary> queryList(QueryObject qo);
}