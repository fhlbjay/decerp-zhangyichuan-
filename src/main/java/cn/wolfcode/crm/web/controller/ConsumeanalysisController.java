package cn.wolfcode.crm.web.controller;

import cn.wolfcode.crm.annotion.PermissionName;
import cn.wolfcode.crm.query.QueryObject;
import cn.wolfcode.crm.service.IConsumecatalogService;
import com.alibaba.fastjson.JSON;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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
@RequestMapping("consumeanalysis")
public class ConsumeanalysisController {
    @Autowired
    private IConsumecatalogService consumecatalogService;

    @RequestMapping("list")
    @RequiresPermissions("consumeanalysis:list")
    @PermissionName("支出分类查看")
    public String list(Model model, @ModelAttribute("qo") QueryObject queryObject) {
        BigDecimal amountByDay = consumecatalogService.countAmountByDay();
        BigDecimal amountByMonth = consumecatalogService.countAmountByMonth();
        BigDecimal amountByYear = consumecatalogService.countAmountByYear();
        model.addAttribute("amountByDay", amountByDay);
        model.addAttribute("amountByMonth", amountByMonth);
        model.addAttribute("amountByYear", amountByYear);

        //line chart  (需使用JSON.toJSONString())
        //|--x轴--月份
        List<String> months = new ArrayList<>();
        //|--y轴--月次支出总计
        List<String> amounts = new ArrayList<>();

        //Map 12个月 默认0
        List<Map<String, Object>> defaultMaps = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("month", String.valueOf(i));
            map.put("amount",String.valueOf(0));
            defaultMaps.add(map);
        }
        System.err.println("defaultMaps : "+ defaultMaps);


        //获得当前表中的month分类的Map数据List
        List<Map<String, Object>> maps = consumecatalogService.selectChartsByMonth();
        System.err.println(maps);


        //进行遍历 将查到的值赋值到默认map中
        if (maps != null) {
            for (Map<String, Object> map : maps) {
                for (Map<String, Object> sMap : defaultMaps) {
                    if(Integer.valueOf(map.get("month").toString()).equals(Integer.valueOf(sMap.get("month").toString()))){
                        sMap.put("amount",map.get("amount"));
                    }
                }
            }
        }
        System.err.println("defaultMaps : "+defaultMaps);


        if (maps != null) {
            for (Map<String, Object> map : defaultMaps) {
                String month = map.get("month").toString() + "月";
                months.add(month);
                String amount = map.get("amount").toString();
                amounts.add(amount);
            }

            model.addAttribute("months", JSON.toJSONString(months));
            model.addAttribute("amounts", JSON.toJSONString(amounts));
            System.err.println("months:" + months);
            System.err.println(JSON.toJSONString(months));
            System.err.println("amounts:" + amounts);
            System.err.println(JSON.toJSONString(amounts));
        }

        //bar chart  支出排列 (需使用JSON.toJSONString())
        //|--x轴--支出分类名称
        List<String> names = new ArrayList<>();
        //|--y轴--该支出分类的总额
        List<String> amounts2 = new ArrayList<>();
        List<Map<String, Object>> maps2 = consumecatalogService.selectChartsByName();

        if (maps2 != null) {
            for (Map<String, Object> map2 : maps2) {
                String name = map2.get("name").toString();
                names.add(name);
                String amount = map2.get("amount").toString();
                amounts2.add(amount);
            }
            model.addAttribute("names", JSON.toJSONString(names));
            model.addAttribute("amounts2", JSON.toJSONString(amounts2));
        }

        return "consumeanalysis";
    }

//    @RequestMapping("view")
//    @RequiresPermissions("consumeanalysis:view")
//    @PermissionName("支出分类列表")
//    @ResponseBody
//    public List<Consumeanalysis> view(@ModelAttribute("qo") QueryObject queryObject) {
//        List<Consumeanalysis> result = consumeanalysisService.selectForParent();
//        return result;
//    }
//    @RequestMapping("viewForParent")
//    @RequiresPermissions("consumeanalysis:viewForParent")
//    @PermissionName("大分类支出列表")
//    public String viewForParent(Model model) {
//        PageResult result = consumeanalysisService.selectForParent();
//        System.err.println(result.toString());
//        model.addAttribute("parent",result);
//        return "consumeanalysis";
//    }

//    @RequestMapping("selectAll")
//    @RequiresPermissions("consumeanalysis:selectAll")
//    @PermissionName("支出分类列表")
//    @ResponseBody
//    public List<Consumeanalysis> selectAll(@ModelAttribute("qo") QueryObject queryObject) {
//        List<Consumeanalysis> consumeanalysiss = consumeanalysisService.selectAll();
//        return consumeanalysiss;
//    }
//
//    @RequestMapping("saveOrUpdate")
//    @RequiresPermissions("consumeanalysis:saveOrUpdate")
//    @PermissionName("支出分类保存或更新")
//    @ResponseBody
//    public JsonResult saveOrUpdate(Consumeanalysis consumeanalysis, Long parent_id) {
//        try {
//
//            if (consumeanalysis.getId() != null) {
//                consumeanalysisService.updateByPrimaryKey(consumeanalysis);
//            } else {
//                consumeanalysisService.insert(consumeanalysis, parent_id);
//            }
//        } catch (Exception e) {
//            return new JsonResult("操作失败");
//        }
//        return new JsonResult();
//    }
//
//    @RequestMapping("delete")
//    @PermissionName("支出分类删除")
//    @RequiresPermissions("consumeanalysis:delete")
//    @ResponseBody
//    public JsonResult delete(Long id) {
//        try {
//            if (id != null) {
//                consumeanalysisService.deleteByPrimaryKey(id);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new JsonResult("删除失败");
//        }
//        return new JsonResult();
//    }

}
