package cn.wolfcode.crm.service;

import cn.wolfcode.crm.domain.Depot;
import cn.wolfcode.crm.query.PageResult;
import cn.wolfcode.crm.query.QueryObject;

import java.util.List;

public interface IDepotService {
    int deleteByPrimaryKey(Long id);

    int insert(Depot record);

    Depot selectByPrimaryKey(Long id);

    List<Depot> selectAll();

    int updateByPrimaryKey(Depot record);

    PageResult query(QueryObject qo);

    void changeStatus(Long id);

    PageResult getStock(QueryObject queryObject, Long id);
}
