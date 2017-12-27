package cn.wolfcode.crm.service.iml;

import cn.wolfcode.crm.mapper.OrderbillItemMapper;
import cn.wolfcode.crm.mapper.VipMapper;
import cn.wolfcode.crm.query.IndexQueryObject;
import cn.wolfcode.crm.query.ProductChartQueryObject;
import cn.wolfcode.crm.query.VipChartQueryObject;
import cn.wolfcode.crm.service.IChartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
@Service
public class IChartServiceImpl implements IChartService {
    @Autowired
    VipMapper vipMapper;
    @Autowired
    OrderbillItemMapper orderbillItemMapper;
    @Override
    public List<Map<String, Object>> vipChart(VipChartQueryObject qo) {
        return vipMapper.vipChart(qo);
    }

    @Override
    public List<Map<String, Object>> dateChart(IndexQueryObject qo) {

        return vipMapper.dateChart(qo);
    }

    @Override
    public List<Map<String, Object>> productChart(ProductChartQueryObject qo) {

        return orderbillItemMapper.productChart(qo);
    }
}
