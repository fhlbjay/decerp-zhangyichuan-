package cn.wolfcode.crm.mapper;

import cn.wolfcode.crm.domain.PurchasingOrderBill;
import cn.wolfcode.crm.query.QueryObject;

import java.util.List;

public interface PurchasingOrderBillMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PurchasingOrderBill record);

    PurchasingOrderBill selectByPrimaryKey(Long id);

    List<PurchasingOrderBill> selectAll();

    int updateByPrimaryKey(PurchasingOrderBill record);

    Integer queryCount(QueryObject queryObject);

    List<PurchasingOrderBill> queryList(QueryObject queryObject);

    void audit(PurchasingOrderBill orderBill);

    void returnBill(PurchasingOrderBill orderBill);

    Integer queryReturnCount(QueryObject qo);

    List<PurchasingOrderBill> queryReturnList(QueryObject qo);

    List<PurchasingOrderBill> selectAllBill();

}