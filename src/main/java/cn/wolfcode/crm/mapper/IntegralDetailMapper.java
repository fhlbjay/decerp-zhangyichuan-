package cn.wolfcode.crm.mapper;

import cn.wolfcode.crm.domain.IntegralDetail;
import cn.wolfcode.crm.query.MyQueryObject;

import java.util.List;

public interface IntegralDetailMapper {
    int deleteByPrimaryKey(Long id);

    int insert(IntegralDetail record);

    IntegralDetail selectByPrimaryKey(Long id);

    List<IntegralDetail> selectAll();

    int updateByPrimaryKey(IntegralDetail record);

    Integer queryRecordCount(MyQueryObject qo);

    List<IntegralDetail> queryRecordList(MyQueryObject qo);
}