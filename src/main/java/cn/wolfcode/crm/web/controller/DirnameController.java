package cn.wolfcode.crm.web.controller;

import cn.wolfcode.crm.annotion.PermissionName;
import cn.wolfcode.crm.domain.Dirname;
import cn.wolfcode.crm.query.QueryObject;
import cn.wolfcode.crm.service.IDirnameService;
import cn.wolfcode.crm.util.JsonResult;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("dirname")
public class DirnameController {
	@Autowired
	private IDirnameService dirnameService;

	@RequestMapping("list")
	@RequiresPermissions("dirname:list")
	@PermissionName("二级菜单查看")
	public String list(@ModelAttribute("qo") QueryObject queryObject) {
		return "dirname";
	}
	

	@RequestMapping("selectAll")
	@RequiresPermissions("dirname:selectAll")
	@PermissionName("二级菜单列表")
	@ResponseBody
	public List<Dirname> selectAll( ) {
		List<Dirname> dirnames = dirnameService.selectAll();
		return dirnames;
	}

	@RequestMapping("selectDirnameBymenuId")
	@RequiresPermissions("dirname:selectDirnameBymenuId")
	@ResponseBody
	public List<Dirname> selectDirnameBymenuId(Long id) {
		List<Dirname> dirnames = dirnameService.selectDirnameBymenuId(id);
		return dirnames;
	}

	@RequestMapping("saveOrUpdate")
	@RequiresPermissions("dirname:saveOrUpdate")
	@PermissionName("二级菜单保存或更新")
	@ResponseBody
	public JsonResult saveOrUpdate(Dirname dirname) {
		try {
			if (dirname.getId()!= null) {
				dirnameService.updateByPrimaryKey(dirname);
			} else {
				dirnameService.insert(dirname);
			}
			return new JsonResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new JsonResult("操作失败");
	}

	@RequestMapping("delete")
	@PermissionName("二级菜单删除")
	@RequiresPermissions("dirname:delete")
	@ResponseBody
	public JsonResult delete(Long id) {
		try {
			if (id != null) {
				dirnameService.deleteByPrimaryKey(id);
			}
		return new JsonResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
			return new JsonResult("删除失败");
	}
	@RequestMapping("view")
	@RequiresPermissions("dirname:view")
	@PermissionName("二级菜单查看")
	@ResponseBody
	public List<Dirname> view(Long id) {
		List<Dirname> result = dirnameService.selectDirnemByParentId(id);
		return result;
	}
}
