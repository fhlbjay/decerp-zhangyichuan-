package cn.wolfcode.crm.mapper;

import cn.wolfcode.crm.domain.Depot;
import cn.wolfcode.crm.query.QueryObject;

import java.util.List;

public interface DepotMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Depot record);

    Depot selectByPrimaryKey(Long id);

    List<Depot> selectAll();

    int updateByPrimaryKey(Depot record);

    Integer queryCount(QueryObject queryObject);

    List<Depot> queryList(QueryObject queryObject);

    void changeStatus(Long id);

}