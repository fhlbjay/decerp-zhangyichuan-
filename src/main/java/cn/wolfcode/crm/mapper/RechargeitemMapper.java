package cn.wolfcode.crm.mapper;

import cn.wolfcode.crm.domain.Rechargeitem;
import cn.wolfcode.crm.query.IndexQueryObject;
import cn.wolfcode.crm.query.QueryObject;

import java.math.BigDecimal;
import java.util.List;

public interface RechargeitemMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Rechargeitem record);

    Rechargeitem selectByPrimaryKey(Long id);

    List<Rechargeitem> selectAll();

    int updateByPrimaryKey(Rechargeitem record);

    Integer queryCount(QueryObject qo);

    List<Rechargeitem> queryList(QueryObject qo);

    List<Rechargeitem> selectRechargeitemByVipId(Long id);

    BigDecimal selectTotalRechargeByVipId(Long vipId);

    BigDecimal selectTotalRecharge(IndexQueryObject qo);

}