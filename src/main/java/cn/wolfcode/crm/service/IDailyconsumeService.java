package cn.wolfcode.crm.service;

import cn.wolfcode.crm.domain.Dailyconsume;
import cn.wolfcode.crm.query.PageResult;
import cn.wolfcode.crm.query.QueryObject;

import java.util.List;

/**
 * Created by Sieg on 2017/12/21.
 */
public interface IDailyconsumeService {
    int deleteByPrimaryKey(Long id);

    int insert(Dailyconsume record);

    Dailyconsume selectByPrimaryKey(Long id);

    List<Dailyconsume> selectAll();

    int updateByPrimaryKey(Dailyconsume record);

    PageResult query(QueryObject queryObject);

}

