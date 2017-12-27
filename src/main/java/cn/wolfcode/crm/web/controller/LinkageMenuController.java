package cn.wolfcode.crm.web.controller;

import cn.wolfcode.crm.annotion.PermissionName;
import cn.wolfcode.crm.domain.LinkageMenu;
import cn.wolfcode.crm.query.PageResult;
import cn.wolfcode.crm.query.QueryObject;
import cn.wolfcode.crm.service.ILinkageMenuService;
import cn.wolfcode.crm.util.JsonResult;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("linkageMenu")
public class LinkageMenuController {
	@Autowired
	private ILinkageMenuService linkageMenuService;

	@RequestMapping("list")
	@RequiresPermissions("linkageMenu:list")
	@PermissionName("一级菜单查看")
	public String list(@ModelAttribute("qo") QueryObject queryObject) {
		return "product/productClassify";
	}

	@RequestMapping("view")
	@RequiresPermissions("linkageMenu:view")
	@PermissionName("一级菜单列表")
	@ResponseBody
	public PageResult view(@ModelAttribute("qo") QueryObject queryObject) {
		PageResult result = linkageMenuService.query(queryObject);
		return result;
	}

	@RequestMapping("selectAll")
	@RequiresPermissions("linkageMenu:selectAll")
	@PermissionName("一级菜单列表")
	@ResponseBody
	public List<LinkageMenu> selectAll() {
		return linkageMenuService.selectAll();
	}

	@RequestMapping("saveOrUpdate")
	@RequiresPermissions("linkageMenu:saveOrUpdate")
	@PermissionName("一级菜单保存或更新")
	@ResponseBody
	public JsonResult saveOrUpdate(LinkageMenu linkageMenu) {
		try {
			if (linkageMenu.getId()!= null) {
				linkageMenuService.updateByPrimaryKey(linkageMenu);
			} else {
				linkageMenuService.insert(linkageMenu);
			}
			return new JsonResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new JsonResult("操作失败");
	}

	@RequestMapping("delete")
	@PermissionName("一级菜单删除")
	@RequiresPermissions("linkageMenu:delete")
	@ResponseBody
	public JsonResult delete(Long id) {
		try {
			if (id != null) {
				System.out.println(66666);
				linkageMenuService.deleteByPrimaryKey(id);
			}
		return new JsonResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
			return new JsonResult("删除失败");
	}

}
