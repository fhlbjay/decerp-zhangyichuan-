package cn.wolfcode.crm.web.controller;

import cn.wolfcode.crm.annotion.PermissionName;
import cn.wolfcode.crm.domain.Supplier;
import cn.wolfcode.crm.query.PageResult;
import cn.wolfcode.crm.query.QueryObject;
import cn.wolfcode.crm.service.ISupplierService;
import cn.wolfcode.crm.util.JsonResult;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("supplier")
public class SupplierController {
	@Autowired
	private ISupplierService supplierService;

	@RequestMapping("list")
	@RequiresPermissions("supplier:list")
	@PermissionName("供应商查看")
	public String list(@ModelAttribute("qo") QueryObject queryObject) {
		return "supplier";
	}

	@RequestMapping("view")
	@RequiresPermissions("supplier:view")
	@PermissionName("供应商列表")
	@ResponseBody
	public PageResult view(@ModelAttribute("qo") QueryObject queryObject) {
		PageResult result = supplierService.query(queryObject);
		return result;
	}

	@RequestMapping("selectAll")
	@RequiresPermissions("supplier:selectAll")
	@PermissionName("供应商列表")
	@ResponseBody
	public List<Supplier> selectAll(@ModelAttribute("qo") QueryObject queryObject) {
		List<Supplier> suppliers = supplierService.selectAll();
		return suppliers;
	}

	@RequestMapping("saveOrUpdate")
	@RequiresPermissions("supplier:saveOrUpdate")
	@PermissionName("供应商保存或更新")
	@ResponseBody
	public JsonResult saveOrUpdate(Supplier supplier) {
		try {
			if (supplier.getId()!= null) {
				supplierService.updateByPrimaryKey(supplier);
			} else {
				supplierService.insert(supplier);
			}
			return new JsonResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new JsonResult("操作失败");
	}

	@RequestMapping("delete")
	@PermissionName("供应商删除")
	@RequiresPermissions("supplier:delete")
	@ResponseBody
	public JsonResult delete(Long id) {
		try {
			if (id != null) {
				supplierService.deleteByPrimaryKey(id);
			}
		return new JsonResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
			return new JsonResult("删除失败");
	}

}
