package cn.wolfcode.crm.mapper;

import cn.wolfcode.crm.domain.Supplier;
import cn.wolfcode.crm.query.QueryObject;

import java.util.List;

public interface SupplierMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Supplier record);

    Supplier selectByPrimaryKey(Long id);

    List<Supplier> selectAll();

    int updateByPrimaryKey(Supplier record);

    Integer queryCount(QueryObject qo);

    List<Supplier> queryList(QueryObject qo);
}