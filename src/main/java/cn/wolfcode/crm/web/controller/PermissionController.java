package cn.wolfcode.crm.web.controller;

import cn.wolfcode.crm.annotion.PermissionName;
import cn.wolfcode.crm.domain.Permission;
import cn.wolfcode.crm.query.PageResult;
import cn.wolfcode.crm.query.QueryObject;
import cn.wolfcode.crm.service.IPermissionService;
import cn.wolfcode.crm.util.JsonResult;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("permission")
public class PermissionController {
    @Autowired
    private IPermissionService permissionService;
    @RequestMapping("list")
    @PermissionName("权限列表")
    @RequiresPermissions("permission:list")
    public String list(@ModelAttribute("qo") QueryObject queryObject) {
        return "permission";
    }
    @RequestMapping("view")
    @PermissionName("权限查看")
    @RequiresPermissions("permission:view")
    @ResponseBody
    public PageResult view(@ModelAttribute("qo") QueryObject queryObject) {
        PageResult result = permissionService.query(queryObject);
        return result;
    }
    @RequestMapping("selectAll")
    @PermissionName("查询所有权限")
    @RequiresPermissions("permission:selectAll")
    @ResponseBody
    public List<Permission> selectAll(@ModelAttribute("qo") QueryObject queryObject) {
        List<Permission> permissions = permissionService.selectAll();
        return permissions;
    }

    @RequestMapping("selectPermissionByRoleId")
    @PermissionName("员工id查询权限")
    @RequiresPermissions("permission:selectPermissionByRoleId")
    @ResponseBody
    public List<Permission> selectPermissionByRoleId(Long id) {
        List<Permission> permissions= permissionService.selectPermissionByRoleId(id);
        return permissions;
    }
    @RequestMapping("loadPermission")
    @PermissionName("加载权限")
    @RequiresPermissions("permission:loadPermission")
    @ResponseBody
    public JsonResult loadPermission() {
        try {
            permissionService.loadPermission();
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult("加载出错");
        }
        return new JsonResult();
    }
    
    
}
