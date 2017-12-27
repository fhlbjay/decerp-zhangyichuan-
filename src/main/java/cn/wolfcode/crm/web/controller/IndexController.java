package cn.wolfcode.crm.web.controller;

import cn.wolfcode.crm.query.IndexQueryObject;
import cn.wolfcode.crm.service.IChartService;
import cn.wolfcode.crm.service.IOrderbillService;
import cn.wolfcode.crm.service.IRechargeitemService;
import cn.wolfcode.crm.service.IVipService;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class IndexController {
    @Autowired
    IVipService vipService;
    @Autowired
    IRechargeitemService rechargeitemService;
    @Autowired
    IOrderbillService orderbillService;
    @Autowired
    IChartService iChartService;
    @RequestMapping("index")
    public String index(){
        return "index";
    }
    @RequestMapping("main")
    public String main(Model model, IndexQueryObject qo){
        qo.getBeginDate();
        qo.getEndDate();
        //方形框所需数据
        //会员累计充值
        BigDecimal totalRecharge =  rechargeitemService.selectTotalRecharge(qo);
        //会员累计消费
        BigDecimal totalConsum = orderbillService.selectTotalConsum(qo);
        //新增会员
        Integer totalNumber = vipService.selectTotalNumber(qo);
        //成交笔数
        Integer totalBill =  orderbillService.selectTotalNum(qo);
        //综合总收入
        if (totalRecharge!=null) {
            if (totalConsum == null) {
                totalConsum = new BigDecimal("0");
            }
            BigDecimal totalIncome = totalRecharge.add(totalConsum);
            model.addAttribute("totalIncome",totalIncome);
        }
        model.addAttribute("totalRecharge",totalRecharge);
        model.addAttribute("totalConsum",totalConsum);
        model.addAttribute("totalNumber",totalNumber);
        model.addAttribute("totalBill",totalBill);
        //处理报表数据 根据时间分组(默认近七天)
        model.addAttribute("days",qo.getDays());
        //获取到分类的类型
        List<Map<String, Object>> maps= iChartService.dateChart(qo);
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
        return "main";
    }
    @RequestMapping("login")
    public String login(){
        return "forward:/login.jsp";
    }
}
