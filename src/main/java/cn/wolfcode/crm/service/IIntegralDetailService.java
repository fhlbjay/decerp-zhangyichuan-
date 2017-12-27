package cn.wolfcode.crm.service;

import cn.wolfcode.crm.domain.IntegralDetail;
import cn.wolfcode.crm.query.MyPageResult;
import cn.wolfcode.crm.query.MyQueryObject;

import java.util.List;

public interface IIntegralDetailService {
    int deleteByPrimaryKey(Long id);

    int insert(IntegralDetail record);

    IntegralDetail selectByPrimaryKey(Long id);

    List<IntegralDetail> selectAll();

    int updateByPrimaryKey(IntegralDetail record);

    MyPageResult query(MyQueryObject qo);

    void clearIntegral(Long vipId);

    void changeIntegral(Long vipId, Long amount, Integer changeType);
}
