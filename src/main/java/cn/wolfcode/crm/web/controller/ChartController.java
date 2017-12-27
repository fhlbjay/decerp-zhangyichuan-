package cn.wolfcode.crm.web.controller;

import cn.wolfcode.crm.query.ProductChartQueryObject;
import cn.wolfcode.crm.query.VipChartQueryObject;
import cn.wolfcode.crm.service.IChartService;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("chart")
public class ChartController {

	/*@RequestMapping("saleChartByBar")
	public String saleChartByBar(Model model,@ModelAttribute("qo")SaleChartQueryObject qo) {
		 //获取到分类的类型
        List<Map<String, Object>> maps = ichartService.saleChart(qo);
        //存放所有的分类名称
        List<String> groupByNames = new ArrayList<>();
        //存放所有的销售总额
        List<String> totalRecharges = new ArrayList<>();
        for (Map<String, Object> map : maps) {
            String groupByName = map.get("groupByName").toString();
            groupByNames.add(groupByName);

            totalRecharges.add(map.get("totalRecharge").toString());
        }
        String groupByName = qo.getGroupByMap().get(qo.getGroupBy());
        model.addAttribute("groupByName", groupByName);
        model.addAttribute("groupByNames", JSON.toJSONString(groupByNames));
        model.addAttribute("totalRecharges", JSON.toJSONString(totalRecharges));
		return "saleChart/bar";
	}
	@RequestMapping("saleChartByPie")
	public String saleChartBypie(Model model,@ModelAttribute("qo")SaleChartQueryObject qo) {
		 String groupByName = qo.getGroupByMap().get(qo.getGroupBy());
	        model.addAttribute("groupByName", groupByName);


	        //获取到分类的类型
	        List<Map<String, Object>> maps = ichartService.saleChart(qo);
	        //存放所有的分类名称
	        List<String> groupByNames = new ArrayList<>();
	        //存放所有的分组类型对应的销售总额
	        List<Map<String,Object>> mapList = new ArrayList<>();
	        BigDecimal max = BigDecimal.ZERO;

	        for (Map<String, Object> map : maps) {
	            Map<String, Object> vipChartMap = new HashMap<>();
	            groupByNames.add(map.get("groupByName").toString());
	            vipChartMap.put("value", map.get("totalRecharge").toString());
	            vipChartMap.put("name", map.get("groupByName").toString());
	            mapList.add(vipChartMap);
	            BigDecimal totalRecharge= new BigDecimal(map.get("totalRecharge").toString());
	            if(totalRecharge.compareTo(max)>0){
	                max=totalRecharge;
	            }
	        }
	        model.addAttribute("groupByNames", JSON.toJSONString(groupByNames));
	        model.addAttribute("mapList", JSON.toJSONString(mapList));
	        model.addAttribute("max", max);
		    return "saleChart/pie";
	}*/
	@Autowired
	IChartService iChartService;
	@RequestMapping("vipchart")
	public String vipChart(Model model, @ModelAttribute("qo")VipChartQueryObject qo){
		List<Map<String, Object>> charts = iChartService.vipChart(qo);
		model.addAttribute("charts",charts);
	    return "vipChart/vipChartList";
    }

	@RequestMapping("vipChartByBar")
	public String vipChartByBar(Model model,@ModelAttribute("qo")VipChartQueryObject qo) {
		//获取到分类的类型
		List<Map<String, Object>> maps = iChartService.vipChart(qo);
		//存放所有的分类名称
		List<String> groupByNames = new ArrayList<>();
		//存放所有的充值总额
		List<String> totalRecharges = new ArrayList<>();
		for (Map<String, Object> map : maps) {
			String groupByName = map.get("groupByName").toString();
			groupByNames.add(groupByName);

			totalRecharges.add(map.get("totalRecharge").toString());
		}
		String groupByName = qo.getGroupByMap().get(qo.getGroupBy());
		model.addAttribute("groupByName", groupByName);
		model.addAttribute("groupByNames", JSON.toJSONString(groupByNames));
		model.addAttribute("totalRecharges", JSON.toJSONString(totalRecharges));
		return "vipChart/bar";
	}
	
	@RequestMapping("vipChartByPie")
	public String vipChartByPie(Model model,@ModelAttribute("qo")VipChartQueryObject qo) {
		String groupByName = qo.getGroupByMap().get(qo.getGroupBy());
		model.addAttribute("groupByName", groupByName);


		//获取到分类的类型
		List<Map<String, Object>> maps = iChartService.vipChart(qo);
		//存放所有的分类名称
		List<String> groupByNames = new ArrayList<>();
		//存放所有的分组类型对应的充值总额
		List<Map<String,Object>> mapList = new ArrayList<>();
		BigDecimal max = BigDecimal.ZERO;

		for (Map<String, Object> map : maps) {
			Map<String, Object> vipChartMap = new HashMap<>();
			groupByNames.add(map.get("groupByName").toString());
			vipChartMap.put("value", map.get("totalRecharge").toString());
			vipChartMap.put("name", map.get("groupByName").toString());
			mapList.add(vipChartMap);
			BigDecimal totalRecharge= new BigDecimal(map.get("totalRecharge").toString());
			if(totalRecharge.compareTo(max)>0){
				max=totalRecharge;
			}
		}
		model.addAttribute("groupByNames", JSON.toJSONString(groupByNames));
		model.addAttribute("mapList", JSON.toJSONString(mapList));
		model.addAttribute("max", max);
		return "vipChart/pie";
	}
	@RequestMapping("productChart")
	public String productChart(Model model, @ModelAttribute("qo")ProductChartQueryObject qo){
		List<Map<String, Object>> charts = iChartService.productChart(qo);
		model.addAttribute("charts",charts);
		return "productChart/list";
	}
	@RequestMapping("productChartByBar")
	public String productChartByBar(Model model,@ModelAttribute("qo")ProductChartQueryObject qo) {
		//获取到分类的类型
		List<Map<String, Object>> maps = iChartService.productChart(qo);
		//存放所有的分类名称
		List<String> groupByNames = new ArrayList<>();
		//存放所有的销售总额
		List<String> totalAmount = new ArrayList<>();
		for (Map<String, Object> map : maps) {
			String groupByName = map.get("groupByName").toString();
			groupByNames.add(groupByName);

			totalAmount.add(map.get("totalAmount").toString());
		}
		String groupByName = qo.getGroupByMap().get(qo.getGroupBy());
		model.addAttribute("groupByName", groupByName);
		model.addAttribute("groupByNames", JSON.toJSONString(groupByNames));
		model.addAttribute("totalRecharges", JSON.toJSONString(totalAmount));
		return "productChart/bar";
	}
	@RequestMapping("productChartByPie")
	public String productChartByPie(Model model,@ModelAttribute("qo")ProductChartQueryObject qo) {
		String groupByName = qo.getGroupByMap().get(qo.getGroupBy());
		model.addAttribute("groupByName", groupByName);
		//获取到分类的类型
		List<Map<String, Object>> maps = iChartService.productChart(qo);
		//存放所有的分类名称
		List<String> groupByNames = new ArrayList<>();
		//存放所有的分组类型对应的充值总额
		List<Map<String,Object>> mapList = new ArrayList<>();
		BigDecimal max = BigDecimal.ZERO;

		for (Map<String, Object> map : maps) {
			Map<String, Object> productChartMap = new HashMap<>();
			groupByNames.add(map.get("groupByName").toString());
			productChartMap.put("value", map.get("totalAmount").toString());
			productChartMap.put("name", map.get("groupByName").toString());
			mapList.add(productChartMap);
			BigDecimal totalAmount= new BigDecimal(map.get("totalAmount").toString());
			if(totalAmount.compareTo(max)>0){
				max=totalAmount;
			}
		}
		model.addAttribute("groupByNames", JSON.toJSONString(groupByNames));
		model.addAttribute("mapList", JSON.toJSONString(mapList));
		model.addAttribute("max", max);
		return "productChart/pie";
	}
}
