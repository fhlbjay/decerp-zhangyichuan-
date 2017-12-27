package cn.wolfcode.crm.web.controller;

import cn.wolfcode.crm.annotion.PermissionName;
import cn.wolfcode.crm.domain.Dictionary;
import cn.wolfcode.crm.query.PageResult;
import cn.wolfcode.crm.query.QueryObject;
import cn.wolfcode.crm.service.IDictionaryService;
import cn.wolfcode.crm.util.JsonResult;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("dictionary")
public class DictionaryController {
	@Autowired
	private IDictionaryService dictionaryService;

	@RequestMapping("list")
	@RequiresPermissions("dictionary:list")
	@PermissionName("数据字典查看")
	public String list(@ModelAttribute("qo") QueryObject queryObject) {
		return "dictionary";
	}

	@RequestMapping("view")
	@RequiresPermissions("dictionary:view")
	@PermissionName("数据字典列表")
	@ResponseBody
	public PageResult view(@ModelAttribute("qo") QueryObject queryObject) {
		PageResult result = dictionaryService.query(queryObject);
		return result;
	}

	@RequestMapping("selectAll")
	@RequiresPermissions("dictionary:selectAll")
	@PermissionName("数据字典列表")
	@ResponseBody
	public List<Dictionary> selectAll() {
		List<Dictionary> dictionarys = dictionaryService.selectAll();
		return dictionarys;
	}

	@RequestMapping("saveOrUpdate")
	@RequiresPermissions("dictionary:saveOrUpdate")
	@PermissionName("数据字典保存或更新")
	@ResponseBody
	public JsonResult saveOrUpdate(Dictionary dictionary) {
		try {
			if (dictionary.getId()!= null) {
				dictionaryService.updateByPrimaryKey(dictionary);
			} else {
				dictionaryService.insert(dictionary);
			}
			return new JsonResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new JsonResult("操作失败");
	}

	@RequestMapping("delete")
	@PermissionName("数据字典删除")
	@RequiresPermissions("dictionary:delete")
	@ResponseBody
	public JsonResult delete(Long id) {
		try {
			if (id != null) {
				dictionaryService.deleteByPrimaryKey(id);
			}
		return new JsonResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
			return new JsonResult("删除失败");
	}

}
