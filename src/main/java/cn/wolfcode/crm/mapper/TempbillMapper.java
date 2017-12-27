package cn.wolfcode.crm.mapper;

import cn.wolfcode.crm.domain.Orderbill;
import cn.wolfcode.crm.query.MyQueryObject;
import cn.wolfcode.crm.query.QueryObject;

import java.util.List;

/**
 * 挂单表
 */
public interface TempbillMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Orderbill record);

    Orderbill selectByPrimaryKey(Long id);

    List<Orderbill> selectAll();

    int updateByPrimaryKey(Orderbill record);

    int queryForCount(MyQueryObject qo);

    List<Orderbill> queryForList(MyQueryObject qo);

    void audit(Orderbill old);


    Integer queryDataCount(QueryObject qo);


    List<Orderbill> queryDataList(QueryObject qo);
}