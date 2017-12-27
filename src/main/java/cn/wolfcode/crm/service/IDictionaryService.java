package cn.wolfcode.crm.service;

import cn.wolfcode.crm.domain.Dictionary;
import cn.wolfcode.crm.query.PageResult;
import cn.wolfcode.crm.query.QueryObject;

import java.util.List;

public interface IDictionaryService {
    void deleteByPrimaryKey(Long id);

    void insert(Dictionary record);

    Dictionary selectByPrimaryKey(Long id);

    List<Dictionary> selectAll();

    void updateByPrimaryKey(Dictionary record);

    PageResult query(QueryObject qo);
}
