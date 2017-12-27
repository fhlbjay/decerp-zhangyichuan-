package cn.wolfcode.crm.web.controller;

import cn.wolfcode.crm.annotion.PermissionName;
import cn.wolfcode.crm.domain.Orderbill;
import cn.wolfcode.crm.domain.Product;
import cn.wolfcode.crm.domain.Vip;
import cn.wolfcode.crm.query.MyPageResult;
import cn.wolfcode.crm.query.OrderbillQueryObject;
import cn.wolfcode.crm.query.PageResult;
import cn.wolfcode.crm.service.IOrderbillService;
import cn.wolfcode.crm.service.IProductService;
import cn.wolfcode.crm.service.IVipService;
import cn.wolfcode.crm.util.JsonResult;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("orderbill")
public class OrderbillController {

    @Autowired
    private IOrderbillService orderbillService;

    @Autowired
    private IProductService productService;
    @Autowired
    private IVipService vipService;

    @RequestMapping("list")
    @PermissionName("订单列表")
    @RequiresPermissions("orderbill:list")
    public String query(@ModelAttribute("qo") OrderbillQueryObject qo, Model model) throws Exception {
        List<Vip> vips = vipService.selectAll();
        model.addAttribute("vips", vips);

        MyPageResult result = orderbillService.query(qo);
        model.addAttribute("result", result);
        return "/orderbill/list";
    }


    @RequestMapping("input")
    @PermissionName("订单编辑")
    @RequiresPermissions("orderbill:input")
    public String input(Model model, Long id) throws Exception {
        List<Vip> vips = vipService.selectAll();
        model.addAttribute("vips", vips);
        if (id != null) {
            Orderbill orderbill = orderbillService.selectByPrimaryKey(id);
            model.addAttribute("orderbill", orderbill);
        }
        return "/orderbill/input";
    }

    @RequestMapping("saveOrUpdate")
    @PermissionName("订单更新/保存")
    @RequiresPermissions("orderbill:saveOrUpdate")
    @ResponseBody
    public JsonResult saveOrUpdate(Orderbill orderbill) throws Exception {
        //保存数据

        if (orderbill.getId() != null) {
            orderbillService.updateByPrimaryKey(orderbill);
        } else {
            orderbillService.insert(orderbill);
        }
        return new JsonResult();
//        return "redirect:/orderbill/list.do";
    }

    @RequestMapping("delete")
    @ResponseBody
    @PermissionName("订单删除")
    @RequiresPermissions("orderbill:delete")
    public JsonResult delete(Long id) throws Exception {

        try {
            if (id != null) {
                orderbillService.deleteByPrimaryKey(id);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonResult("删除订单成功");
        }
        return new JsonResult();
//        return "redirect:/orderbill/query.do";
    }


    //商品选择按钮
    @RequestMapping("selectProductList")
    public String selectProductList(Model model) throws Exception {
        List<Product> products = productService.selectAll();
        model.addAttribute("products", products);
        return "/orderbill/selectProductList";
    }


    @RequestMapping("view")
    @PermissionName("订单查看")
    @RequiresPermissions("orderbill:view")
    public String view(Long id, Model model) throws Exception {
        List<Vip> vips = vipService.selectAll();
        model.addAttribute("vips", vips);

        if (id != null) {
            Orderbill orderbill = orderbillService.selectByPrimaryKey(id);
            model.addAttribute("orderbill", orderbill);
        }
        return "/orderbill/view";
    }




   /* @RequestMapping("audit")
    @PermissionName("订单审核")
    @RequiresPermissions("orderbill:list")
    public String audit(Long id) throws Exception {

        orderbillService.audit(id);
        return "redirect:/orderbill/list.do";
    }*/


}
