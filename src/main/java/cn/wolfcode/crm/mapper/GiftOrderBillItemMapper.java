package cn.wolfcode.crm.mapper;

import cn.wolfcode.crm.domain.GiftOrderBillItem;
import cn.wolfcode.crm.query.MyQueryObject;

import java.util.List;

public interface GiftOrderBillItemMapper {
    int deleteByPrimaryKey(Long id);

    int insert(GiftOrderBillItem record);

    GiftOrderBillItem selectByPrimaryKey(Long id);

    List<GiftOrderBillItem> selectAll();

    int updateByPrimaryKey(GiftOrderBillItem record);

    Integer queryCount(MyQueryObject qo);

    List<GiftOrderBillItem> queryList(MyQueryObject qo);

    Integer queryRecordCount(MyQueryObject qo);

    List<GiftOrderBillItem> queryRecordList(MyQueryObject qo);

    List<GiftOrderBillItem> queryExpordRecord(MyQueryObject qo);
}