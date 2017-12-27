package cn.wolfcode.crm.web.controller;

import cn.wolfcode.crm.annotion.PermissionName;
import cn.wolfcode.crm.query.PageResult;
import cn.wolfcode.crm.query.QueryObject;
import cn.wolfcode.crm.service.IInventoryService;
import cn.wolfcode.crm.util.JsonResult;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("inventory")
public class InventoryController {
	@Autowired
	private IInventoryService inventoryService;

	@RequestMapping("list")
	@RequiresPermissions("inventory:list")
	@PermissionName("盘点查看")
	public String list() {
		return "inventory";
	}

	@RequestMapping("view")
	@RequiresPermissions("inventory:view")
	@PermissionName("盘点列表")
	@ResponseBody
	public PageResult view(@ModelAttribute("qo") QueryObject queryObject) {
		PageResult result = inventoryService.query(queryObject);
		return result;
	}

	@RequestMapping("selectNumber")
	@RequiresPermissions("inventory:selectNumber")
	@PermissionName("数量确认")
	@ResponseBody
	public JsonResult selectNumber(Long pId,Long dId,Long iId,Integer number) {
		String message = inventoryService.selectNumber(pId, dId,iId, number);
		return new JsonResult(message);
	}

	@RequestMapping("changeNumber")
	@RequiresPermissions("inventory:changeNumber")
	@PermissionName("数量确认")
	@ResponseBody
	public JsonResult changeNumber(Long pId,Long dId,Long psId,Long iId,Integer number,Integer newNumber) {
		try {
			inventoryService.changeNumber(pId, dId,iId,psId, number,newNumber);
			return new JsonResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new JsonResult("操作失败");
	}


	@RequestMapping("getItems")
	@RequiresPermissions("inventory:getItems")
	@PermissionName("盘点记录")
	@ResponseBody
	public PageResult getItems(@ModelAttribute("qo") QueryObject queryObject,Long id) {
		return	inventoryService.getItems(queryObject,id);
	}

}
