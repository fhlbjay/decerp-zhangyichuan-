package cn.wolfcode.crm.service;

import cn.wolfcode.crm.domain.PurchasingOrderBill;
import cn.wolfcode.crm.query.PageResult;
import cn.wolfcode.crm.query.QueryObject;

import java.util.List;

public interface IPurchasingOrderBillService {
    int deleteByPrimaryKey(Long id);

    int insert(PurchasingOrderBill record);

    PurchasingOrderBill selectByPrimaryKey(Long id);

    List<PurchasingOrderBill> selectAll();

    int updateByPrimaryKey(PurchasingOrderBill record);

    PageResult query(QueryObject qo);

    PageResult getItemsById(Long id);

    void audit(Long id);

    void returnBill(Long id);

    PageResult queryReturn(QueryObject queryObject);

    List<PurchasingOrderBill> selectAllBill();
}
