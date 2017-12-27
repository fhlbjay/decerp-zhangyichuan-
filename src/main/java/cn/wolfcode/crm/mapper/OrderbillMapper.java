package cn.wolfcode.crm.mapper;

import cn.wolfcode.crm.domain.Orderbill;
import cn.wolfcode.crm.query.IndexQueryObject;
import cn.wolfcode.crm.query.MyQueryObject;

import java.math.BigDecimal;
import java.util.List;

public interface OrderbillMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Orderbill record);

    Orderbill selectByPrimaryKey(Long id);

    List<Orderbill> selectAll();

    int updateByPrimaryKey(Orderbill record);

    int queryForCount(MyQueryObject qo);

    List<Orderbill> queryForList(MyQueryObject qo);


    BigDecimal selectTotalConsum(IndexQueryObject qo);

    Integer selectTotalNum(IndexQueryObject qo);

}