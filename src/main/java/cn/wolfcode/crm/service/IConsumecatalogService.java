package cn.wolfcode.crm.service;

import cn.wolfcode.crm.domain.Consumecatalog;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created by Sieg on 2017/12/22.
 */
public interface IConsumecatalogService {
    int deleteByPrimaryKey(Long id);

    int insert(Consumecatalog record, Long parentId);

    Consumecatalog selectByPrimaryKey(Long id);

    List<Consumecatalog> selectAll();

    int updateByPrimaryKey(Consumecatalog record);

    List<Consumecatalog> selectForParent();

    List<Consumecatalog> selectForChildren(Long id);

    BigDecimal countAmountByDay();

    BigDecimal countAmountByMonth();

    BigDecimal countAmountByYear();

    List<Map<String,Object>> selectChartsByMonth();

    List<Map<String,Object>> selectChartsByName();
}
