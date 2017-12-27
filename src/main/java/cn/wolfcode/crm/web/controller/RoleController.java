package cn.wolfcode.crm.web.controller;

import cn.wolfcode.crm.annotion.PermissionName;
import cn.wolfcode.crm.domain.Role;
import cn.wolfcode.crm.query.PageResult;
import cn.wolfcode.crm.query.QueryObject;
import cn.wolfcode.crm.service.IRoleService;
import cn.wolfcode.crm.util.JsonResult;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("role")
public class RoleController {
    @Autowired
    private IRoleService roleService;
    @RequestMapping("list")
    @PermissionName("角色查看")
    @RequiresPermissions("role:list")
    public String list(@ModelAttribute("qo") QueryObject queryObject) {
        return "role";
    }
    @RequestMapping("view")
    @PermissionName("角色列表")
    @RequiresPermissions("role:view")
    @ResponseBody
    public PageResult view(@ModelAttribute("qo") QueryObject queryObject) {
        PageResult result = roleService.query(queryObject);
        return result;
    }
    @RequestMapping("selectAll")
    @PermissionName("角色列表")
    @RequiresPermissions("role:selectAll")
    @ResponseBody
    public List<Role> selectAll(@ModelAttribute("qo") QueryObject queryObject) {
        List<Role> roles = roleService.selectAll();
        return roles;
    }
    @RequestMapping("saveOrUpdate")
    @PermissionName("角色保存或更新")
    @RequiresPermissions("role:saveOrUpdate")
    @ResponseBody
    public JsonResult saveOrUpdate(Role role) {
        try {

            if (role.getId() != null) {
                roleService.updateByPrimaryKey(role);
            } else {
                roleService.insert(role);
            }
        }catch (Exception e){
            return new JsonResult("操作失败");
        }
        return new JsonResult();
    }
    @RequestMapping("delete")
    @PermissionName("角色删除")
    @RequiresPermissions("role:delete")
    @ResponseBody
    public JsonResult delete(Long id) {
        try {
            if (id != null) {
                roleService.deleteByPrimaryKey(id);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return  new JsonResult("删除失败");
        }
        return new JsonResult();
    }
}
