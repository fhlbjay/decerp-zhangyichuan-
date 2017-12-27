package cn.wolfcode.crm.service;

import cn.wolfcode.crm.domain.GiftOrderBillItem;
import cn.wolfcode.crm.query.MyGiftExchangeRecordOject;
import cn.wolfcode.crm.query.MyPageResult;
import cn.wolfcode.crm.query.MyQueryObject;

import java.util.List;

public interface IGiftOrderBillItemService {
    int deleteByPrimaryKey(Long id);

    int insert(List<GiftOrderBillItem> record);

    GiftOrderBillItem selectByPrimaryKey(Long id);

    List<GiftOrderBillItem> selectAll();

    int updateByPrimaryKey(GiftOrderBillItem record);

    MyPageResult query(MyQueryObject qo);

    MyPageResult queryRecord(MyQueryObject qo);

    List<GiftOrderBillItem> queryExpordRecord(MyGiftExchangeRecordOject qo);
}
