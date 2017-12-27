package cn.wolfcode.crm.web.controller;

import cn.wolfcode.crm.annotion.PermissionName;
import cn.wolfcode.crm.domain.Consumecatalog;
import cn.wolfcode.crm.query.QueryObject;
import cn.wolfcode.crm.service.IConsumecatalogService;
import cn.wolfcode.crm.service.IEmployeeService;
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
@RequestMapping("consumecatalog")
public class ConsumecatalogController {
    @Autowired
    private IConsumecatalogService consumecatalogService;
    @Autowired
    private IEmployeeService employeeService;

    @RequestMapping("list")
    @RequiresPermissions("consumecatalog:list")
    @PermissionName("支出分类查看")
    public String list(Model model,@ModelAttribute("qo") QueryObject queryObject) {
        List<Consumecatalog> result = consumecatalogService.selectForParent();
        System.err.println(result.toString());
        model.addAttribute("parent",result);
        return "consumecatalog";
    }

//    @RequestMapping("view")
//    @RequiresPermissions("consumecatalog:view")
//    @PermissionName("支出分类列表")
//    @ResponseBody
//    public List<Consumecatalog> view(@ModelAttribute("qo") QueryObject queryObject) {
//        List<Consumecatalog> result = consumecatalogService.selectForParent();
//        return result;
//    }
//    @RequestMapping("viewForParent")
//    @RequiresPermissions("consumecatalog:viewForParent")
//    @PermissionName("大分类支出列表")
//    public String viewForParent(Model model) {
//        PageResult result = consumecatalogService.selectForParent();
//        System.err.println(result.toString());
//        model.addAttribute("parent",result);
//        return "consumecatalog";
//    }

    @RequestMapping("selectAll")
    @RequiresPermissions("consumecatalog:selectAll")
    @PermissionName("支出分类列表")
    @ResponseBody
    public List<Consumecatalog> selectAll(@ModelAttribute("qo") QueryObject queryObject) {
        List<Consumecatalog> consumecatalogs = consumecatalogService.selectAll();
        return consumecatalogs;
    }

    @RequestMapping("saveOrUpdate")
    @RequiresPermissions("consumecatalog:saveOrUpdate")
    @PermissionName("支出分类保存或更新")
    @ResponseBody
    public JsonResult saveOrUpdate(Consumecatalog consumecatalog, Long parent_id) {
        try {

            if (consumecatalog.getId() != null) {
                consumecatalogService.updateByPrimaryKey(consumecatalog);
            } else {
                consumecatalogService.insert(consumecatalog, parent_id);
            }
        } catch (Exception e) {
            return new JsonResult("操作失败");
        }
        return new JsonResult();
    }

    @RequestMapping("delete")
    @PermissionName("支出分类删除")
    @RequiresPermissions("consumecatalog:delete")
    @ResponseBody
    public JsonResult delete(Long id) {
        try {
            if (id != null) {
                consumecatalogService.deleteByPrimaryKey(id);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonResult("删除失败");
        }
        return new JsonResult();
    }

}
