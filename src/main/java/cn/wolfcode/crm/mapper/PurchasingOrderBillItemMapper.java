package cn.wolfcode.crm.mapper;

import cn.wolfcode.crm.domain.PurchasingOrderBillItem;

public interface PurchasingOrderBillItemMapper {

    int insert(PurchasingOrderBillItem record);

    void deleteByBillId(Long id);

}