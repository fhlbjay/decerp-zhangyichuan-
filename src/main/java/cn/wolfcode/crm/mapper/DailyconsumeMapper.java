package cn.wolfcode.crm.mapper;

import cn.wolfcode.crm.domain.Dailyconsume;
import cn.wolfcode.crm.domain.Employee;
import cn.wolfcode.crm.query.QueryObject;

import java.util.List;

public interface DailyconsumeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Dailyconsume record);

    Dailyconsume selectByPrimaryKey(Long id);

    List<Dailyconsume> selectAll();

    int updateByPrimaryKey(Dailyconsume record);

    Integer queryCount(QueryObject qo);

    List<Dailyconsume> queryList(QueryObject qo);

    Employee selectConsumerByConsumeId (Long id);
}