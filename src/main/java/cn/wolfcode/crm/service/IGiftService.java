package cn.wolfcode.crm.service;

import cn.wolfcode.crm.domain.Gift;
import cn.wolfcode.crm.query.MyPageResult;
import cn.wolfcode.crm.query.MyQueryObject;

import java.util.List;

public interface IGiftService {
    int deleteByPrimaryKey(Long id);

    int insert(Gift record);

    Gift selectByPrimaryKey(Long id);

    List<Gift> selectAll();

    int updateByPrimaryKey(Gift record);

    MyPageResult query(MyQueryObject qo);
}
