package cn.wolfcode.crm.service;

import cn.wolfcode.crm.domain.Rechargeitem;
import cn.wolfcode.crm.query.IndexQueryObject;
import cn.wolfcode.crm.query.PageResult;
import cn.wolfcode.crm.query.QueryObject;

import java.math.BigDecimal;
import java.util.List;

public interface IRechargeitemService {
    int deleteByPrimaryKey(Long id);

    int insert(Rechargeitem record);

    Rechargeitem selectByPrimaryKey(Long id);

    List<Rechargeitem> selectAll();

    int updateByPrimaryKey(Rechargeitem record);

    PageResult query(QueryObject qo);

    List<Rechargeitem> selectRechargeitemByVipId(QueryObject queryObject, Long id);

    BigDecimal selectTotalRecharge(IndexQueryObject qo);

}
