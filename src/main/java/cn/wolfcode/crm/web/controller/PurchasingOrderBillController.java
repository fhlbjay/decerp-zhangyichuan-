package cn.wolfcode.crm.web.controller;

import cn.wolfcode.crm.annotion.PermissionName;
import cn.wolfcode.crm.domain.PurchasingOrderBill;
import cn.wolfcode.crm.query.PageResult;
import cn.wolfcode.crm.query.PurchasingOrderBillQueryObject;
import cn.wolfcode.crm.service.IPurchasingOrderBillService;
import cn.wolfcode.crm.util.JsonResult;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.DateTime;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.List;

@Controller
@RequestMapping("purchasingOrderBill")
public class PurchasingOrderBillController {
	@Autowired
	IPurchasingOrderBillService purchasingOrderBillService;

	@RequestMapping("view")
	@RequiresPermissions("purchasingOrderBill:view")
	@PermissionName("采购订单列表")
	@ResponseBody
	public PageResult view(@ModelAttribute("qo") PurchasingOrderBillQueryObject queryObject) {
		//使用if-else方式做权限判断
		Subject subject = SecurityUtils.getSubject();
		//在任意地方获取采购订单对象
		System.out.println(subject.getPrincipal());
		if (subject.hasRole("admin")) {
			System.out.println("有admin角色");
		} else {
			System.out.println("没有admin角色");
		}
		PageResult result = purchasingOrderBillService.query(queryObject);
		return result;
	}

	@RequestMapping("list")
	@PermissionName("采购订单查看")
	@RequiresPermissions("purchasingOrderBill:list")
	public String list(@ModelAttribute("qo") PurchasingOrderBillQueryObject queryObject) {
		return "purchasingOrderBill";
	}

	@RequestMapping("saveOrUpdate")
	@ResponseBody
	public JsonResult saveOrUpdate(PurchasingOrderBill purchasingOrderBill) {
		try {

			if (purchasingOrderBill.getId() != null) {
				purchasingOrderBillService.updateByPrimaryKey(purchasingOrderBill);
			} else {
				purchasingOrderBillService.insert(purchasingOrderBill);
			}
			return new JsonResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new JsonResult("操作失败");
	}

	@RequestMapping("delete")
	@RequiresPermissions("purchasingOrderBill:delete")
	@PermissionName("采购订单删除")
	@ResponseBody
	public JsonResult delete(Long id) {
		try {
			if (id != null) {
				purchasingOrderBillService.deleteByPrimaryKey(id);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult("删除失败");
		}
		return new JsonResult();
	}

	@RequestMapping("exportXls")
	public void exportXls(HttpServletResponse response) throws Exception {
		//文件下载响应头
		response.setHeader("Content-Disposition", "attachment;filename=purchasingOrderBill.xls");
		//创建xls文件
		WritableWorkbook workbook = Workbook.createWorkbook(response.getOutputStream());
		//创建工作簿
		WritableSheet sheet = workbook.createSheet("day01", 0);
		//获取真实的采购订单数据
		List<PurchasingOrderBill> purchasingOrderBills = purchasingOrderBillService.selectAllBill();
		//添加标题
		sheet.addCell(new Label(0, 0, "订单编号"));
		sheet.addCell(new Label(1, 0, "供应商"));
		sheet.addCell(new Label(2, 0, "业务时间"));
		sheet.addCell(new Label(3, 0, "总数量"));
		sheet.addCell(new Label(4, 0, "总金额"));
		sheet.addCell(new Label(5, 0, "录入人"));
		sheet.addCell(new Label(6, 0, "审核人"));
		sheet.addCell(new Label(7, 0, "状态"));
		for (int i = 0, j = 1; i < purchasingOrderBills.size(); i++, j++) {
			PurchasingOrderBill purchasingOrderBill = purchasingOrderBills.get(i);

            /*//创建文本单元格
            Label label = new Label(0,0,"");*/

			//往工作簿中添加内容

			sheet.addCell(new Label(0, j, purchasingOrderBill.getSn()));
			sheet.addCell(new Label(1, j, purchasingOrderBill.getSupplier().getName()));
			sheet.addCell(new DateTime(2, j, purchasingOrderBill.getVdate()));
			sheet.addCell(new Label(3, j, purchasingOrderBill.getTotalNumber().toString()));
			sheet.addCell(new Label(4, j, purchasingOrderBill.getTotalAmount().toString()));
			sheet.addCell(new Label(5, j, purchasingOrderBill.getInputUser().getUsername()));
			sheet.addCell(new Label(6, j, purchasingOrderBill.getAuditor().getUsername()));
			sheet.addCell(new Label(7, j, purchasingOrderBill.getStatus().toString()));
		}
		//写入数据
		workbook.write();

		//关闭资源
		workbook.close();
	}

	@RequestMapping("importXls")
	@ResponseBody
	public JsonResult importXls(MultipartFile file) throws Exception {
		Workbook workbook = null;
		try {
			//读取xls文件
			workbook = Workbook.getWorkbook(file.getInputStream());
			//读取某个工作簿
			Sheet sheet = workbook.getSheet(0);
			//获取总行数
			int rows = sheet.getRows();

			for (int i = 0; i < rows; i++) {
				PurchasingOrderBill purchasingOrderBill = new PurchasingOrderBill();
				purchasingOrderBill.setSn(sheet.getCell(0, i).getContents());
				purchasingOrderBill.getSupplier().setName(sheet.getCell(1, i).getContents());
				purchasingOrderBill.setVdate(new SimpleDateFormat().parse(sheet.getCell(2,i).getContents()));
				purchasingOrderBill.setTotalNumber(new BigDecimal(sheet.getCell(3,i).getContents()));
				purchasingOrderBill.setTotalAmount(new BigDecimal(sheet.getCell(4,i).getContents()));
				purchasingOrderBill.getInputUser().setUsername(sheet.getCell(5,i).getContents());
				purchasingOrderBill.getAuditor().setUsername(sheet.getCell(6,i).getContents());
				purchasingOrderBill.getAuditor().setState(new Boolean(sheet.getCell(7,i).getContents()));
				purchasingOrderBillService.insert(purchasingOrderBill);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult("导入错误");
		} finally {
			if (workbook != null) {
				workbook.close();
			}
		}
		return new JsonResult();
	}

	@RequestMapping("getItemsById")
	@ResponseBody
	public PageResult getItemsById(Long id){
		return purchasingOrderBillService.getItemsById(id);
	}


	@RequestMapping("audit")
	@RequiresPermissions("purchasingOrderBill:audit")
	@PermissionName("采购订单审核")
	@ResponseBody
	public JsonResult audit(Long id) {
		try {
			if (id != null) {
				purchasingOrderBillService.audit(id);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult("审核失败");
		}
		return new JsonResult();
	}


	@RequestMapping("returnBill")
	@RequiresPermissions("purchasingOrderBill:returnBill")
	@PermissionName("退货")
	@ResponseBody
	public JsonResult returnBill(Long id) {
		try {
			if (id != null) {
				purchasingOrderBillService.returnBill(id);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult("退货失败");
		}
		return new JsonResult();
	}


	@RequestMapping("return")
	@PermissionName("采购退货查看")
	@RequiresPermissions("purchasingOrderBill:return")
	public String list() {
		return "purchasingOrderBillReturn";
	}

	@RequestMapping("viewReturn")
	@RequiresPermissions("purchasingOrderBill:viewReturn")
	@PermissionName("采购退货列表")
	@ResponseBody
	public PageResult viewReturn(@ModelAttribute("qo") PurchasingOrderBillQueryObject queryObject) {
		PageResult result = purchasingOrderBillService.queryReturn(queryObject);
		return result;
	}


}
