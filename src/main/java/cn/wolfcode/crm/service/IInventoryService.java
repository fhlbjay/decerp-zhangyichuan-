package cn.wolfcode.crm.service;

import cn.wolfcode.crm.domain.Inventory;
import cn.wolfcode.crm.query.PageResult;
import cn.wolfcode.crm.query.QueryObject;

public interface IInventoryService {

    int insert(Inventory inventory);

    PageResult query(QueryObject qo);

    String selectNumber(Long pId, Long dId,Long iId, Integer number);

    void changeNumber(Long pId, Long dId,Long psId, Long iId, Integer number,Integer newNumber);

    PageResult getItems(QueryObject queryObject, Long id);
}
