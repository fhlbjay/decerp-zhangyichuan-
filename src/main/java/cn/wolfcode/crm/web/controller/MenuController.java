package cn.wolfcode.crm.web.controller;

import cn.wolfcode.crm.annotion.PermissionName;
import cn.wolfcode.crm.domain.Menu;
import cn.wolfcode.crm.query.PageResult;
import cn.wolfcode.crm.query.QueryObject;
import cn.wolfcode.crm.service.IMenuService;
import cn.wolfcode.crm.util.JsonResult;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("menu")
public class MenuController {
    @Autowired
    private IMenuService menuService;
    @RequestMapping("list")
    @PermissionName("菜单查看")
    @RequiresPermissions("menu:list")
    public String list(@ModelAttribute("qo") QueryObject queryObject) {
        return "menu";
    }
    @RequestMapping("view")
    @PermissionName("菜单列表")
    @RequiresPermissions("menu:view")
    @ResponseBody
    public PageResult view(@ModelAttribute("qo") QueryObject queryObject) {
        PageResult result = menuService.query(queryObject);
        return result;
    }
    @RequestMapping("selectAll")
    @PermissionName("菜单列表")
    @RequiresPermissions("menu:selectAll")
    @ResponseBody
    public List<Menu> selectAll(@ModelAttribute("qo") QueryObject queryObject) {
        List<Menu> menus = menuService.selectAll();
        return menus;
    }

    @RequestMapping("getParentIdById")
    @PermissionName("菜单列表")
    @RequiresPermissions("menu:getParentIdById")
    public Long getParentIdById(@ModelAttribute("qo") QueryObject queryObject) {
        return menuService.getParentIdById(queryObject.getParentId());

    }
    @RequestMapping("getMenus")
    @PermissionName("菜单列表")
    @RequiresPermissions("menu:getMenus")
    @ResponseBody
    public Object getMenus(Long id) {
        /*//先从session中取出该用户的菜单集合
        Session session = SecurityUtils.getSubject().getSession();
        Object menuData = session.getAttribute("MENU_DATA");
        session.removeAttribute("MENU_DATA");
        //如果session中没有,就需要查询数据库
        if (menuData == null) {
            menuData= menuService.getMenus(id);
            //对菜单做过滤操作
            MenuUtil.checkPermssion((List<Menu>)menuData);
            //放入session中
           session.setAttribute("MENU_DATA",menuData);

        }*/

       return menuService.getMenus(id);
    }
    @RequestMapping("saveOrUpdate")
    @PermissionName("菜单保存或更新")
    @RequiresPermissions("menu:saveOrUpdate")
    @ResponseBody
    public JsonResult saveOrUpdate(Menu menu) {
        try {

            if (menu.getId() != null) {
                menuService.updateByPrimaryKey(menu);
            } else {
                menuService.insert(menu);
            }
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult("操作失败");
        }
        return new JsonResult();
    }
    @RequestMapping("delete")
    @PermissionName("菜单删除")
    @RequiresPermissions("menu:delete")
    @ResponseBody
    public JsonResult delete(Long id) {
        try {
            if (id != null) {
                menuService.deleteByPrimaryKey(id);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return  new JsonResult("删除失败");
        }
        return new JsonResult();
    }
}
