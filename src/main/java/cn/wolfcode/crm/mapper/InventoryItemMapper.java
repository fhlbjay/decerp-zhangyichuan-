package cn.wolfcode.crm.mapper;

import cn.wolfcode.crm.domain.Inventory;
import cn.wolfcode.crm.domain.InventoryItem;
import cn.wolfcode.crm.query.QueryObject;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface InventoryItemMapper {

    int insert(InventoryItem record);

    int queryCount(@Param("qo") QueryObject qo,@Param("id") Long id);

    List<Inventory> queryList(@Param("qo") QueryObject qo,@Param("id") Long id);

}