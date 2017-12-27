package cn.wolfcode.crm.web.controller;

import cn.wolfcode.crm.annotion.PermissionName;
import cn.wolfcode.crm.domain.Department;
import cn.wolfcode.crm.query.PageResult;
import cn.wolfcode.crm.query.QueryObject;
import cn.wolfcode.crm.service.IDepartmentService;
import cn.wolfcode.crm.util.JsonResult;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("department")
public class DepartmentController {
	@Autowired
	private IDepartmentService departmentService;

	@RequestMapping("list")
	@RequiresPermissions("department:list")
	@PermissionName("部门查看")
	public String list(@ModelAttribute("qo") QueryObject queryObject) {
		return "department";
	}

	@RequestMapping("view")
	@RequiresPermissions("department:view")
	@PermissionName("部门列表")
	@ResponseBody
	public PageResult view(@ModelAttribute("qo") QueryObject queryObject) {
		PageResult result = departmentService.query(queryObject);
		return result;
	}

	@RequestMapping("selectAll")
	@RequiresPermissions("department:selectAll")
	@PermissionName("部门列表")
	@ResponseBody
	public List<Department> selectAll(@ModelAttribute("qo") QueryObject queryObject) {
		List<Department> departments = departmentService.selectAll();
		return departments;
	}

	@RequestMapping("saveOrUpdate")
	@RequiresPermissions("department:saveOrUpdate")
	@PermissionName("部门保存或更新")
	@ResponseBody
	public JsonResult saveOrUpdate(Department department) {
		try {
			if (department.getId()!= null) {
				departmentService.updateByPrimaryKey(department);
			} else {
				departmentService.insert(department);
			}
			return new JsonResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new JsonResult("操作失败");
	}

	@RequestMapping("delete")
	@PermissionName("部门删除")
	@RequiresPermissions("department:delete")
	@ResponseBody
	public JsonResult delete(Long id) {
		try {
			if (id != null) {
				departmentService.deleteByPrimaryKey(id);
			}
		return new JsonResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
			return new JsonResult("删除失败");
	}

}
