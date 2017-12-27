package cn.wolfcode.crm.web.controller;

import cn.wolfcode.crm.annotion.PermissionName;
import cn.wolfcode.crm.domain.Orderbill;
import cn.wolfcode.crm.domain.Product;
import cn.wolfcode.crm.domain.Shopingcar;
import cn.wolfcode.crm.query.MyProductQueryObject;
import cn.wolfcode.crm.query.PageResult;
import cn.wolfcode.crm.query.QueryObject;
import cn.wolfcode.crm.service.IOrderbillService;
import cn.wolfcode.crm.service.IProductService;
import cn.wolfcode.crm.service.ITempbillService;
import cn.wolfcode.crm.util.JsonResult;
import com.alibaba.fastjson.JSONArray;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("cash")
public class CashController {

    @Autowired
    private IOrderbillService orderbillService;
    @Autowired
    private ITempbillService tempbillService;
    @Autowired
    private IProductService productService;


    @RequestMapping("view")
    public String cashView() {
        return "/cash/cash";
    }


    @RequestMapping("list")
    public String list() {
        return "nopermission";
    }

    //商品界面

    @RequestMapping("productList")
    @ResponseBody
    public PageResult productList(MyProductQueryObject qo) throws Exception {
        System.out.println(qo);
        PageResult result = productService.query(qo);
        return result;
    }


    //结账
    @RequestMapping("saveOrUpdate")
    @PermissionName("结账")
    @RequiresPermissions("cash:saveOrUpdate")
    @ResponseBody
    public JsonResult cashend(Long vipid) throws Exception {
//        保存数据
        Boolean judge = orderbillService.judgeShopcar();
        if (!judge) {
            return new JsonResult("请选择商品");
        }
        try {
            orderbillService.saveBill(vipid);
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonResult("结账失败");
        }
        return new JsonResult();
    }

    //结账校验密码
    @RequestMapping("checkVip")
    @PermissionName("结账校验密码")
    @RequiresPermissions("cash:checkVip")
    @ResponseBody
    public JsonResult checkVip(String vipcardSn, String password) throws Exception {
        try {

            //这里传过来的是vipid  书写错误
            orderbillService.checkVip(vipcardSn, password);
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonResult("密码或者账号错误");
        }
        return new JsonResult();
    }


    //挂单
    @RequestMapping("tempbill")
    @PermissionName("挂单")
    @RequiresPermissions("cash:tempbill")
    @ResponseBody
    public JsonResult tempbill(Long vipid) throws Exception {
        Boolean judge = orderbillService.judgeShopcar();

        //保存数据
        try {
            if (!judge) {
                return new JsonResult("请选择商品");
            } else {
                tempbillService.saveTempBill(vipid);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return new JsonResult("挂单失败");
        }
        return new JsonResult();
    }


    @RequestMapping("cashGetTemp")
    @PermissionName("取单界面")
    @RequiresPermissions("cash:cashGetTemp")
    @ResponseBody
    public PageResult cashGetTemp(QueryObject qo) throws Exception {
        return tempbillService.cashGetTemp(qo);
    }

    @RequestMapping("getTempbill")
    @PermissionName("取单之后数据跳转")
    @RequiresPermissions("cash:getTempbill")
    @ResponseBody
    public JsonResult getTempbill(Long id) throws Exception {
        try {
            tempbillService.getTempbill(id);
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonResult("取单失败");
        }
        return new JsonResult();
    }


    //购物车添加商品
    @RequestMapping("shopadd")
    @PermissionName("添加商品")
    @RequiresPermissions("cash:cashGetTemp")
    @ResponseBody
    public JsonResult shopadd(Long id) throws Exception {


        try {
            tempbillService.shopadd(id);
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonResult("加入购物车失败");
        }

        return new JsonResult();
    }

    //购物车添加商品
    @RequestMapping("shopAll")
    @PermissionName("购物车表格更新")
    @RequiresPermissions("cash:shopAll")
    @ResponseBody
    public PageResult shopAll(QueryObject qo) throws Exception {
        PageResult result = tempbillService.shopAll(qo);
        return result;

    }

    //购物车添加商品
    @RequestMapping("clearall")
    @PermissionName("购物车清空")
    @RequiresPermissions("cash:clearall")
    @ResponseBody
    public JsonResult clearall() throws Exception {
        try {
            tempbillService.clearall();
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonResult("清空失败");
        }
        return new JsonResult();

    }

    //购物车中删除一个 productId
    @RequestMapping("removeOne")
    @PermissionName("购物车删除一个")
    @RequiresPermissions("cash:removeOne")
    @ResponseBody
    public JsonResult removeOne(Long productId) throws Exception {
        System.out.println(productId);
        try {
            tempbillService.removeOne(productId);
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonResult("删除失败");
        }
        return new JsonResult();

    }

    //取单页面删除一个
    @RequestMapping("deleteOneTempbill")
    @PermissionName("取单页面删除一个")
    @RequiresPermissions("cash:deleteOneTempbill")
    @ResponseBody
    public JsonResult deleteOneTempbill(Long id) throws Exception {
        try {
            System.out.println(id);
            tempbillService.deleteOneTempbill(id);
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonResult("删除失败");
        }
        return new JsonResult();

    }

    //计算总价折扣的初始化
    @RequestMapping("amountForm")
    @PermissionName("计算总价,折扣")
    @RequiresPermissions("cash:amountForm")
    @ResponseBody
    public Object amountForm(Long vipid) throws Exception {
        Map<String, Object> map = tempbillService.amountForm(vipid);
        return map;


    }

    //取单时候计算总价 折扣初始化
    @RequestMapping("amountFormGetemp")
    @PermissionName("取单计算总价,折扣")
    @RequiresPermissions("cash:amountFormGetemp")
    @ResponseBody
    public Object amountFormGetemp() throws Exception {
        Map<String, Object> map = tempbillService.amountFormGetemp();
        return map;


    }


}
