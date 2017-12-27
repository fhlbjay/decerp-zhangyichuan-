package cn.wolfcode.crm.mapper;


import cn.wolfcode.crm.domain.OrderbillItem;
import cn.wolfcode.crm.query.ProductChartQueryObject;

import java.util.List;
import java.util.Map;

public interface OrderbillItemMapper {
	int deleteByPrimaryKey(Long id);

	int insert(OrderbillItem record);

	void deleteByOrederbillKey(Long id);

	List<Map<String, Object>> productChart(ProductChartQueryObject qo);

	List<Map<String,Object>> selectTop();
}