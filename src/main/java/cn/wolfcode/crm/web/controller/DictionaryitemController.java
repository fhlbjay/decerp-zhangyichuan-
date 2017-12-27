package cn.wolfcode.crm.web.controller;

import cn.wolfcode.crm.annotion.PermissionName;
import cn.wolfcode.crm.domain.Dictionaryitem;
import cn.wolfcode.crm.query.PageResult;
import cn.wolfcode.crm.service.IDictionaryitemService;
import cn.wolfcode.crm.util.JsonResult;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("dictionaryitem")
public class DictionaryitemController {
	@Autowired
	private IDictionaryitemService dictionaryitemService;

	@RequestMapping("list")
	@RequiresPermissions("dictionaryitem:list")
	@PermissionName("数据字典明细查看")
	public String list() {
		return "dictionaryitem";
	}

	@RequestMapping("view")
	@RequiresPermissions("dictionaryitem:view")
	@PermissionName("数据字典明细列表")
	@ResponseBody
	public PageResult view(String sn) {
		PageResult result = dictionaryitemService.query(sn);
		return result;
	}
	@RequestMapping("saveOrUpdate")
	@RequiresPermissions("dictionaryitem:saveOrUpdate")
	@PermissionName("数据字典明细保存或更新")
	@ResponseBody
	public JsonResult saveOrUpdate( Dictionaryitem dictionaryitem) {
		try {
			if (dictionaryitem.getId()!= null) {
				dictionaryitemService.updateByPrimaryKey(dictionaryitem);
			} else {
				dictionaryitemService.insert(dictionaryitem);
			}
			return new JsonResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new JsonResult("操作失败");
	}

	@RequestMapping("delete")
	@PermissionName("数据字典明细删除")
	@RequiresPermissions("dictionaryitem:delete")
	@ResponseBody
	public JsonResult delete(Long id) {
		try {
			if (id != null) {
				dictionaryitemService.deleteByPrimaryKey(id);
			}
		return new JsonResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
			return new JsonResult("删除失败");
	}

}
