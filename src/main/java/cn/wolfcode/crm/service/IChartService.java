package cn.wolfcode.crm.service;

import cn.wolfcode.crm.query.IndexQueryObject;
import cn.wolfcode.crm.query.ProductChartQueryObject;
import cn.wolfcode.crm.query.VipChartQueryObject;

import java.util.List;
import java.util.Map;

public interface IChartService {
    //会员报表
    List<Map<String,Object>> vipChart (VipChartQueryObject qo);
    //主页报表
    List<Map<String,Object>> dateChart(IndexQueryObject qo);
    //产品报表
    List<Map<String,Object>> productChart(ProductChartQueryObject qo);

}
