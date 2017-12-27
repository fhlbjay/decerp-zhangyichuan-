package cn.wolfcode.crm.mapper;


import cn.wolfcode.crm.domain.OrderbillItem;

import java.util.List;

public interface TempbillItemMapper {
    int deleteByPrimaryKey(Long id);

    int insert(OrderbillItem record);

    void deleteByOrederbillKey(Long id);

    List<OrderbillItem> selectByTempbillId(Long id);
}