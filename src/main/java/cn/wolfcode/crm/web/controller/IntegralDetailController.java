package cn.wolfcode.crm.web.controller;

import cn.wolfcode.crm.query.MyIntegralDetailQueryObject;
import cn.wolfcode.crm.query.MyPageResult;
import cn.wolfcode.crm.service.IIntegralDetailService;
import cn.wolfcode.crm.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("integralDetail")
public class IntegralDetailController {
    @Autowired
    IIntegralDetailService integralDetailService;
    //跳转到积分页面
    @RequestMapping("list")
    public String list() {
        return "integralDetail/list";
    }

    //积分记录表
    @ResponseBody
    @RequestMapping("selectIntegralDetail")
    public Object selectIntegralDetail(MyIntegralDetailQueryObject qo) {
        MyPageResult result = integralDetailService.query(qo);
        return result;
    }

    //积分清零
    @ResponseBody
    @RequestMapping("clearIntegral")
    public Object clearIntegral(Long vipId) {
        try {
            integralDetailService.clearIntegral(vipId);
        } catch (Exception e) {
            return new JsonResult(e.getMessage());
        }
        return new JsonResult();
    }

    //积分变动
    @ResponseBody
    @RequestMapping("changeIntegral")
    public Object changeIntegral(Long vipId,Long amount,Integer changeType) {
        try {
            integralDetailService.changeIntegral(vipId,amount,changeType);
        } catch (Exception e) {
            return new JsonResult(e.getMessage());
        }
        return new JsonResult();
    }
}
