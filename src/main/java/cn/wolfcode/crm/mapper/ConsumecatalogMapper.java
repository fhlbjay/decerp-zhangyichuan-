package cn.wolfcode.crm.mapper;

import cn.wolfcode.crm.domain.Consumecatalog;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface ConsumecatalogMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Consumecatalog record);

    Consumecatalog selectByPrimaryKey(Long id);

    List<Consumecatalog> selectAll();

    int updateByPrimaryKey(Consumecatalog record);

    List<Consumecatalog> selectForParent();

    List<Consumecatalog> selectForChildren(Long id);

    Integer queryCount();

    BigDecimal countAmountByDay();

    BigDecimal countAmountByMonth();

    BigDecimal countAmountByYear();

    List<Map<String,Object>> selectChartsByMonth();

    List<Map<String,Object>> selectChartsByName();
}