package cn.wolfcode.crm.web.controller;

import cn.wolfcode.crm.annotion.PermissionName;
import cn.wolfcode.crm.domain.Depot;
import cn.wolfcode.crm.query.PageResult;
import cn.wolfcode.crm.query.QueryObject;
import cn.wolfcode.crm.service.IDepotService;
import cn.wolfcode.crm.util.JsonResult;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("depot")
public class DepotController {
	@Autowired
	private IDepotService depotService;

	@RequestMapping("list")
	@RequiresPermissions("depot:list")
	@PermissionName("仓库查看")
	public String list(@ModelAttribute("qo") QueryObject queryObject) {
		return "depot";
	}

	@RequestMapping("view")
	@RequiresPermissions("depot:view")
	@PermissionName("仓库列表")
	@ResponseBody
	public PageResult view(@ModelAttribute("qo") QueryObject queryObject) {
		PageResult result = depotService.query(queryObject);
		return result;
	}

	@RequestMapping("selectAll")
	@RequiresPermissions("depot:selectAll")
	@PermissionName("仓库列表")
	@ResponseBody
	public List<Depot> selectAll(@ModelAttribute("qo") QueryObject queryObject) {
		List<Depot> depots = depotService.selectAll();
		return depots;
	}

	@RequestMapping("saveOrUpdate")
	@RequiresPermissions("depot:saveOrUpdate")
	@PermissionName("仓库保存或更新")
	@ResponseBody
	public JsonResult saveOrUpdate(Depot depot) {
		try {
			if (depot.getId()!= null) {
				depotService.updateByPrimaryKey(depot);
			} else {
				depotService.insert(depot);
			}
			return new JsonResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new JsonResult("操作失败");
	}

	@RequestMapping("delete")
	@PermissionName("仓库删除")
	@RequiresPermissions("depot:delete")
	@ResponseBody
	public JsonResult delete(Long id) {
		try {
			if (id != null) {
				depotService.deleteByPrimaryKey(id);
			}
		return new JsonResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
			return new JsonResult("删除失败");
	}

	@RequestMapping("changeStatus")
	@PermissionName("仓库状态改变")
	@RequiresPermissions("department:changeStatus")
	@ResponseBody
	public JsonResult changeStatus(Long id) {
		depotService.changeStatus(id);
		return new JsonResult();
	}

	@RequestMapping("getStock")
	@RequiresPermissions("depot:getStock")
	@PermissionName("仓库列表")
	@ResponseBody
	public PageResult getStock(@ModelAttribute("qo") QueryObject queryObject,Long id) {
		PageResult result = depotService.getStock(queryObject,id);
		return result;
	}
}
