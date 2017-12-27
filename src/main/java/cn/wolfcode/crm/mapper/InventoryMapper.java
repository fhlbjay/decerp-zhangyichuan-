package cn.wolfcode.crm.mapper;

import cn.wolfcode.crm.domain.Inventory;
import cn.wolfcode.crm.query.QueryObject;

import java.util.List;

public interface InventoryMapper {

    int insert(Inventory inventory);

    int queryCount(QueryObject qo);

    List<Inventory> queryList(QueryObject qo);

    void update(Long id);

}