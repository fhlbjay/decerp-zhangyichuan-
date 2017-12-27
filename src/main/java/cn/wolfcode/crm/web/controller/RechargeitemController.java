package cn.wolfcode.crm.web.controller;

import cn.wolfcode.crm.annotion.PermissionName;
import cn.wolfcode.crm.domain.Rechargeitem;
import cn.wolfcode.crm.query.PageResult;
import cn.wolfcode.crm.query.QueryObject;
import cn.wolfcode.crm.service.IRechargeitemService;
import cn.wolfcode.crm.util.JsonResult;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("rechargeitem")
public class RechargeitemController {
    @Autowired
    private IRechargeitemService rechargeitemService;


    @RequestMapping("selectAll")
    @RequiresPermissions("rechargeitem:selectAll")
    @PermissionName("充值明细列表")
    @ResponseBody
    public List<Rechargeitem> selectAll(@ModelAttribute("qo") QueryObject queryObject) {
        List<Rechargeitem> rechargeitems = rechargeitemService.selectAll();
        return rechargeitems;
    }

    @RequestMapping("selectRechargeitemByVipId")
    @RequiresPermissions("rechargeitem:selectRechargeitemByVipId")
    @PermissionName("充值明细列表")
    @ResponseBody
    public PageResult selectRechargeitemByVipId(@ModelAttribute("qo") QueryObject queryObject) {
        PageResult result = rechargeitemService.query(queryObject);
        return result;
    }
    @RequestMapping("save")
    @RequiresPermissions("rechargeitem:save")
    @PermissionName("充值明细保存或更新")
    @ResponseBody
    public JsonResult saveOrUpdate(Rechargeitem rechargeitem) {
        try {
            rechargeitemService.insert(rechargeitem);
            return new JsonResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new JsonResult("操作失败");
    }

    @RequestMapping("delete")
    @PermissionName("充值明细删除")
    @RequiresPermissions("rechargeitem:delete")
    @ResponseBody
    public JsonResult delete(Long id) {
        try {
            if (id != null) {
                rechargeitemService.deleteByPrimaryKey(id);
            }
            return new JsonResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new JsonResult("删除失败");
    }

}
