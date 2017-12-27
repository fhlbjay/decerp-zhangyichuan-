package cn.wolfcode.crm.service;

import cn.wolfcode.crm.domain.Dictionaryitem;
import cn.wolfcode.crm.query.PageResult;

public interface IDictionaryitemService {
    void deleteByPrimaryKey(Long id);

    void insert(Dictionaryitem record);

    void updateByPrimaryKey(Dictionaryitem record);

    PageResult query(String sn);
}
