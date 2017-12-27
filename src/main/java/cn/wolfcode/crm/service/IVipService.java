package cn.wolfcode.crm.service;

import cn.wolfcode.crm.domain.Vip;
import cn.wolfcode.crm.query.IndexQueryObject;
import cn.wolfcode.crm.query.PageResult;
import cn.wolfcode.crm.query.QueryObject;

import java.math.BigDecimal;
import java.util.List;

public interface IVipService {
    int deleteByPrimaryKey(Long id);

    int insert(Vip record);

    Vip selectByPrimaryKey(Long id);

    List<Vip> selectAll();

    int updateByPrimaryKey(Vip record);

    PageResult query(QueryObject qo);

    void changeState(Long id);

    Integer selectBirthdayOfToday();

    Integer selectBirthdayOfMonth();

    Integer selectVipNumber();

    BigDecimal selectConsumer();

    BigDecimal selectConsumptionAmount();

    Integer selectOrderBilNum();

    List<String> selectConsumptionAmountTop();

    Integer selectTotalNumber(IndexQueryObject qo);

}
